package org.treemaker;

import org.treemaker.parsing.AmbiguousSentence;
import org.treemaker.phrases.Phrase;
import org.treemaker.parsing.Sentence;
import org.treemaker.tree.Tree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:dizionario.db";
        String input = "il gatto ruota";
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
            Sentence s = as.solve();
            System.out.println(s);
            st.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}