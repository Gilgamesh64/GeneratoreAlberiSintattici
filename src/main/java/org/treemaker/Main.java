package org.treemaker;

import org.treemaker.parsing.AmbiguousSentence;
import org.treemaker.parsing.Phrase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:dizionario.db";
        String input = "la pesca ruota";
        String[] words = input.split(" ", -1);

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement st = conn.createStatement();
            var pstmt = conn.prepareStatement("SELECT * FROM parole WHERE forma = ?");

            AmbiguousSentence as = new AmbiguousSentence();

            for (String chiave : words) {
                pstmt.setString(1, chiave);

                var rs = pstmt.executeQuery();

                while (rs.next()) {
                    String forma = rs.getString("forma");
                    String lemma = rs.getString("lemma");
                    String caratteristiche = rs.getString("caratteristiche");
                    as.add(Phrase.from(forma, lemma, caratteristiche));

                }
                rs.close();
            }

            System.out.println(as);
            st.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}