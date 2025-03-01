package com.revature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String url = "jdbc:postgresql://localhost:5432/project_cero";
    private static final String user = "postgres";
    private static final String password = "123";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // private static Connection connection = null;


    // public static Connection getConnection(){
    //     if(connection == null){
    //         try {
    //             connection = DriverManager.getConnection(url, user, password);

    //             System.out.println("#### CONECTATO :)");
                
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    //     return connection;
         
    // }
}
