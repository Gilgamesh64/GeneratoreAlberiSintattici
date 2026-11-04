package org.example;

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

            for(String forma : parole){
                pstmt.setString(1, forma);

                var rs = pstmt.executeQuery();

                System.out.println("-----------------------------------------------------------------------------------------");
                while (rs.next()) {
                    System.out.print("||  forma: " + rs.getString("forma") + "  ||  ");
                    System.out.print("||  lemma: " + rs.getString("lemma") + "  ||  ");
                    System.out.println("||  caratteristiche: " + rs.getString("caratteristiche") + "  ||");
                }
                System.out.println("-----------------------------------------------------------------------------------------");
                rs.close();
            }

            st.close();
            pstmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}