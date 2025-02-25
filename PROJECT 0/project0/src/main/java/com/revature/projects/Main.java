package com.revature.projects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/project_cero";
        String user = "postgres";
        String password = "123";

        String sql = "CREATE TABLE Account (" +
                     "id SERIAL PRIMARY KEY, " +
                     "Email VARCHAR(255) NOT NULL, " +
                     "Password VARCHAR(255) NOT NULL)";
        
        try (Connection connection = DriverManager.getConnection(url, user, password);
        Statement stmt = connection.createStatement()){

            stmt.execute(sql);
            System.out.println("Table Created :) ");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}