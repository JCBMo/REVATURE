package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.revature.projects.controller.AccountCONTROLLER;
import com.revature.projects.controller.ManagerCONTROLLER;
import com.revature.projects.dao.AccountDAO;
import com.revature.projects.dao.ManagerDAO;
import com.revature.projects.services.AccountSERVICES;
import com.revature.projects.services.ManagerSERVICES;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        dataBaseSetup();

        AccountSERVICES accountSERVICES = new AccountSERVICES(new AccountDAO());
        AccountCONTROLLER accountCONTROLLER = new AccountCONTROLLER(accountSERVICES);

        ManagerSERVICES managerSERVICES = new ManagerSERVICES(new ManagerDAO());
        ManagerCONTROLLER managerCONTROLLER = new ManagerCONTROLLER(managerSERVICES);


        Javalin app = accountCONTROLLER.startAPI();
        app.start(7070);
     
    }


    public static void dataBaseSetup(){
        try {
            Connection connection = DataBaseConnection.getConnection();

            PreparedStatement dropTable = connection.prepareStatement("DROP TABLE IF EXISTS Account CASCADE;");
            dropTable.executeUpdate();

            PreparedStatement dropTableManager = connection.prepareStatement("DROP TABLE IF EXISTS Manager CASCADE;");
            dropTable.executeUpdate();

            PreparedStatement createTableAccount = connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS Account(
                        id_account SERIAL PRIMARY KEY,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        password VARCHAR(100)NOT NULL,
                        is_manager BOOLEAN
                    )
                    """);
            createTableAccount.executeUpdate();

            PreparedStatement createTableManager = connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS Manager(
                        id_manager SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        phone INT NOT NULL UNIQUE,

                        id_account INT INIQUE REFERENCES Account(id_account),
                    )
                    """);
            createTableAccount.executeUpdate();

            PreparedStatement insertInfoAccount = connection.prepareStatement("""
                    INSERT INTO Account (email, password, is_manager)
                    VALUES
                        ('email@gmail.com', '123', true)
                    """);
            insertInfoAccount.executeUpdate();

            PreparedStatement insertInfoManager = connection.prepareStatement("""
                    INSERT INTO Manager (name, phone. id_account)
                    VALUES
                        ('John Doe', 5525259988, 1)
                    """);
            insertInfoAccount.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}