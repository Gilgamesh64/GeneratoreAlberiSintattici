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
        String input = "il gatto mangia";
        String[] words = input.split(" ", -1);

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement st = conn.createStatement();
            var pstmt = conn.prepareStatement("SELECT * FROM words WHERE form = ?");

            AmbiguousSentence as = new AmbiguousSentence();

            for (String key : words) {
                pstmt.setString(1, key);

                var rs = pstmt.executeQuery();

                while (rs.next()) {
                    String form = rs.getString("form");
                    String lemma = rs.getString("lemma");
                    String features = rs.getString("features");
                    as.add(Phrase.from(form, lemma, features));
                }
                rs.close();
            }
            Sentence s = as.solve();
            System.out.println(s);

            Tree tree = new Tree(s);
            System.out.println(tree);

            st.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}