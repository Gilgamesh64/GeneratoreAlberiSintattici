package org.treemaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:dizionario.db";
        System.out.print("Inserisci la parola da cercare: >");
        String frase = "la pesca ruota";
        String[] parole = frase.split(" ", -1);

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement st = conn.createStatement();
            var pstmt = conn.prepareStatement("SELECT * FROM parole WHERE forma = ?");
            System.out.println();

            for (String chiave : parole) {
                pstmt.setString(1, chiave);

                var rs = pstmt.executeQuery();

                while (rs.next()) {
                    String forma = rs.getString("forma");
                    String lemma = rs.getString("lemma");
                    String caratteristiche = rs.getString("caratteristiche");
                    System.out.println(forma + " " + lemma + " " + caratteristiche);
                }
                rs.close();
            }

            st.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}