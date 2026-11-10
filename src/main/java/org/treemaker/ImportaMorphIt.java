package org.treemaker;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class ImportaMorphIt {

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

                    st.execute("""
                        CREATE TABLE parole (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            forma TEXT NOT NULL,
                            lemma TEXT NOT NULL,
                            caratteristiche TEXT NOT NULL,
                            UNIQUE(forma, lemma, caratteristiche)
                        )
                    """);
                }

                String sql = """
                    INSERT INTO parole
                    (forma, lemma, caratteristiche)
                    VALUES (?, ?, ?)
                """;

                int totale = 0;
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

                        String[] campi = line.split("\t", -1);

                        if (campi.length != 3) {
                            continue;
                        }

                        String forma = campi[0].trim();
                        String lemma = campi[1].trim();
                        String caratteristiche = campi[2].trim();

                        if (forma.isEmpty() || lemma.isEmpty()) {
                            continue;
                        }

                        ps.setString(1, forma);
                        ps.setString(2, lemma);
                        ps.setString(3, caratteristiche);

                        ps.addBatch();

                        batch++;
                        totale++;

                        if (batch >= BATCH_SIZE) {
                            ps.executeBatch();
                            conn.commit();

                            batch = 0;

                            System.out.println(
                                    "Importate " + totale + " righe..."
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
                             "SELECT COUNT(*) FROM parole")) {

                    if (rs.next()) {
                        System.out.println(
                                "Record effettivi: " + rs.getInt(1)
                        );
                    }
                }

                System.out.println("Importazione completata!");

            } catch (Exception e) {

                conn.rollback();
                throw e;
            }
        }
    }
}