package org.treeMaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:dizionario.db";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci la parola da cercare: >");
        String frase = scanner.nextLine();
        String[] parole = frase.split(" ", -1);

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement st = conn.createStatement();
            var pstmt = conn.prepareStatement("SELECT * FROM parole WHERE forma = ?");

            for (String chiave : parole) {
                pstmt.setString(1, chiave);

                var rs = pstmt.executeQuery();

                while (rs.next()) {
                    String forma = rs.getString("forma");
                    String lemma = rs.getString("lemma");
                    String caratteristiche = rs.getString("caratteristiche");
                    rs.close();
                }
            }

            st.close();
            pstmt.close();
            scanner.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}