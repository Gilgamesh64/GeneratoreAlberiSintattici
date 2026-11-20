package org.treemaker;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class ImportMorphIt {

    private static final int BATCH_SIZE = 1000;

    public static void main(String[] args) throws Exception {

        Path fileInput = Path.of("morph-it_048.txt");
        String database = "dizionario.db";

        String url = "jdbc:sqlite:" + database;

        try (Connection conn =
                     DriverManager.getConnection(url)) {

            conn.setAutoCommit(false);

            try {
                // Rebuild table from scratch
                try (Statement st = conn.createStatement()) {

                    st.execute("DROP TABLE IF EXISTS parole");
                    st.execute("DROP TABLE IF EXISTS words");

                    st.execute("""
                        CREATE TABLE words (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            form TEXT NOT NULL,
                            lemma TEXT NOT NULL,
                            features TEXT NOT NULL,
                            UNIQUE(form, lemma, features)
                        )
                    """);
                }

                String sql = """
                    INSERT INTO words
                    (form, lemma, features)
                    VALUES (?, ?, ?)
                """;

                int total = 0;
                int batch = 0;

                try (BufferedReader reader =
                             Files.newBufferedReader(
                                     fileInput,
                                     StandardCharsets.ISO_8859_1);

                     PreparedStatement ps =
                             conn.prepareStatement(sql)) {

                    String line;

                    while ((line = reader.readLine()) != null) {

                        if (line.isBlank()) {
                            continue;
                        }

                        line = line.replace("\uFEFF", "");

                        String[] fields = line.split("\t", -1);

                        if (fields.length != 3) {
                            continue;
                        }

                        String form = fields[0].trim();
                        String lemma = fields[1].trim();
                        String features = fields[2].trim();

                        if (form.isEmpty() || lemma.isEmpty()) {
                            continue;
                        }

                        ps.setString(1, form);
                        ps.setString(2, lemma);
                        ps.setString(3, features);

                        ps.addBatch();

                        batch++;
                        total++;

                        if (batch >= BATCH_SIZE) {
                            ps.executeBatch();
                            conn.commit();

                            batch = 0;

                            System.out.println(
                                    "total rows imported: " + total
                            );
                        }
                    }

                    if (batch > 0) {
                        ps.executeBatch();
                    }
                }

                conn.commit();

                // Statistics
                try (Statement st = conn.createStatement();
                     var rs = st.executeQuery(
                             "SELECT COUNT(*) FROM words")) {

                    if (rs.next()) {
                        System.out.println(
                                "Records: : " + rs.getInt(1)
                        );
                    }
                }

                System.out.println("Success!");

            } catch (Exception e) {

                conn.rollback();
                throw e;
            }
        }
    }
}