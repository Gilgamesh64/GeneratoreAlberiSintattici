
package org.treemaker;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Server {

    private static final Path PUBLIC_DIR = Path.of("public").toAbsolutePath().normalize();

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080), 0);

        
        // API endpoint
        server.createContext("/message", exchange -> {

            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
                return;
            }

            String message = new String(
                    exchange.getRequestBody().readAllBytes(),
                    StandardCharsets.UTF_8);

            System.out.println("Received: " + message);

            String json = DatabaseManager.getTree(message);

            byte[] response = json.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set(
                    "Content-Type", "application/json; charset=UTF-8");

            exchange.sendResponseHeaders(200, response.length);

            exchange.getResponseBody().write(response);
            exchange.close();
        });


        // Static website files
        server.createContext("/", exchange -> {

            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())
                    && !"HEAD".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
                return;
            }

            try {
                String requestedPath = exchange.getRequestURI().getPath();

                if (requestedPath.equals("/")) {
                    requestedPath = "/index.html";
                }

                Path file = PUBLIC_DIR.resolve(
                        requestedPath.substring(1)).normalize();

                // Prevent directory traversal
                if (!file.startsWith(PUBLIC_DIR)
                        || !Files.isRegularFile(file)) {
                    exchange.sendResponseHeaders(404, -1);
                    exchange.close();
                    return;
                }

                byte[] content = Files.readAllBytes(file);

                String contentType = getContentType(file);

                exchange.getResponseHeaders().set(
                        "Content-Type", contentType);

                exchange.getResponseHeaders().set(
                        "X-Content-Type-Options", "nosniff");

                exchange.sendResponseHeaders(
                        200,
                        exchange.getRequestMethod().equals("HEAD")
                                ? -1
                                : content.length);

                if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                    exchange.getResponseBody().write(content);
                }

                exchange.close();

            } catch (Exception e) {
                e.printStackTrace();
                exchange.close();
            }
        });

        server.start();

        System.out.println("Server running at http://localhost:8080");
    }

    private static String getContentType(Path file) {

        String name = file.getFileName().toString();

        if (name.endsWith(".html"))
            return "text/html; charset=UTF-8";
        if (name.endsWith(".js"))
            return "application/javascript; charset=UTF-8";
        if (name.endsWith(".css"))
            return "text/css; charset=UTF-8";
        if (name.endsWith(".json"))
            return "application/json; charset=UTF-8";
        if (name.endsWith(".png"))
            return "image/png";
        if (name.endsWith(".jpg") || name.endsWith(".jpeg"))
            return "image/jpeg";
        if (name.endsWith(".svg"))
            return "image/svg+xml";
        if (name.endsWith(".ico"))
            return "image/x-icon";

        return "application/octet-stream";
    }
}