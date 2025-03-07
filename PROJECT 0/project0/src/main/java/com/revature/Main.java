package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import com.revature.projects.controller.AccountCONTROLLER;
import com.revature.projects.controller.CustomerCONTROLLER;
import com.revature.projects.controller.LoanCONTROLLER;
import com.revature.projects.dao.AccountDAO;
import com.revature.projects.dao.CustomerDAO;
import com.revature.projects.dao.LoanDao;
import com.revature.projects.services.AccountSERVICES;
import com.revature.projects.services.CustomerSERVICE;
import com.revature.projects.services.LoanSERVICES;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        dataBaseSetup();

        // ManagerSERVICES managerSERVICES = new ManagerSERVICES(new ManagerDAO());
        // ManagerCONTROLLER managerCONTROLLER = new ManagerCONTROLLER(managerSERVICES);

        AccountSERVICES accountSERVICES = new AccountSERVICES(new AccountDAO());
        AccountCONTROLLER accountCONTROLLER = new AccountCONTROLLER(accountSERVICES);

        LoanSERVICES loanSERVICES = new LoanSERVICES(new LoanDao());
        LoanCONTROLLER loanCONTROLLER = new LoanCONTROLLER(loanSERVICES);

        CustomerSERVICE customerSERVICE = new CustomerSERVICE(new CustomerDAO());
        CustomerCONTROLLER customerCONTROLLER = new CustomerCONTROLLER(customerSERVICE);

        Javalin app = Javalin.create(config -> {
             // Habilitar sesiones
        });

        //Javalin app = Javalin.create();
       // managerCONTROLLER.addRoutes(app);
        accountCONTROLLER.addRoutes(app);
        loanCONTROLLER.addRoutes(app);
        customerCONTROLLER.addRoutes(app);

        app.exception(NumberFormatException.class, (e, ctx) -> {
        ctx.status(400).json(Map.of("error", "INVALID ID***"));
        });

        app.start(7070);
     
    }


    public static void dataBaseSetup(){
        try {
            Connection connection = DataBaseConnection.getConnection();

            // TABLES ACCOUNT***
            PreparedStatement dropTable = connection.prepareStatement("DROP TABLE IF EXISTS Account CASCADE;");
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

            PreparedStatement insertInfoAccount = connection.prepareStatement("""
                    INSERT INTO Account (email, password, is_manager)
                    VALUES
                        ('manager_@gmail.com', 'password', true),
                        ('customer_@gmail.com', 'password', false)
                    """);
            insertInfoAccount.executeUpdate();

            //TABLE LOAN**
            PreparedStatement dropTableLoan = connection.prepareStatement("DROP TABLE IF EXISTS Loan CASCADE;");
            dropTableLoan.executeUpdate();

            PreparedStatement createTableLoan = connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS Loan(
                        id_loan SERIAL PRIMARY KEY,
                        amount INT NOT NULL,
                        status VARCHAR(100)NOT NULL,
                        aplication_date DATE NOT NULL, 
                        id_account INT NOT NULL,
                        FOREIGN KEY (id_account) REFERENCES Account(id_account)
                    )
                    """);
            createTableLoan.executeUpdate();

            PreparedStatement insertInfoLoan = connection.prepareStatement("""
                    INSERT INTO Loan (amount, status, aplication_date, id_account)
                    VALUES
                        (5000, 'Active', '2025-3-07', 1)
                    """);
            insertInfoLoan.executeUpdate();



            //TABLE CUSTOMER**
            PreparedStatement dropTableCustomer = connection.prepareStatement("DROP TABLE IF EXISTS Customer CASCADE;");
            dropTableCustomer.executeUpdate();

            PreparedStatement createTableCustomer = connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS Customer(
                        name VARCHAR(100) NOT NULL,
                        age INTEGER NOT NULL,
                        phone VARCHAR(100) NOT NULL,
                        salary INTEGER NOT NULL, 
                        id_account INT NOT NULL,
                        Id_loan INT NOT NULL,
                        
                        FOREIGN KEY (id_account) REFERENCES Account(id_account),
                        FOREIGN KEY (id_loan) REFERENCES Loan(id_loan)
                      
                    )
                    """);
            createTableCustomer.executeUpdate();

            PreparedStatement insertInfoCustomer = connection.prepareStatement("""
                    INSERT INTO Customer (name, age, phone, salary, id_account, id_loan)
                    VALUES
                        ('Nemo_manager', 32, '55259988', 1000, 1, 1)
                    """);
            insertInfoCustomer.executeUpdate();








            // //TABLE ADDRESS**
            // PreparedStatement dropTableAddres = connection.prepareStatement("DROP TABLE IF EXISTS Address CASCADE;");
            // dropTableAddres.executeUpdate();

            // PreparedStatement createTableAddres = connection.prepareStatement("""
            //         CREATE TABLE IF NOT EXISTS Addres(
            //             id_address SERIAL PRIMARY KEY,
            //             state VARCHAR(100) NOT NULL,
            //             city VARCHAR(100) NOT NULL,
            //             zip INT NOT NULL, 
            //             street VARCHAR(100) NOT NULL
            //         )
            //         """);
            // createTableAddres.executeUpdate();

            // PreparedStatement insertInfoAddress = connection.prepareStatement("""
            //         INSERT INTO Address (state, city, zip, street)
            //         VALUES
            //             ('Sydney', 'Wallaby Way', 42, 'P Sharman')
            //         """);
            // insertInfoAddress.executeUpdate();


            // // TABLE MANAGER***
            // PreparedStatement dropTableManager = connection.prepareStatement("DROP TABLE IF EXISTS Manager CASCADE;");
            // dropTableManager.executeUpdate();

            // PreparedStatement createTableManager = connection.prepareStatement("""
            //         CREATE TABLE IF NOT EXISTS Manager(
            //             id_manager SERIAL PRIMARY KEY,
            //             name VARCHAR(100) NOT NULL,
            //             phone VARCHAR(100) NOT NULL UNIQUE,

            //             id_account INT UNIQUE REFERENCES Account(id_account)
            //         )
            //         """);
            // createTableManager.executeUpdate();

            // PreparedStatement insertInfoManager = connection.prepareStatement("""
            //         INSERT INTO Manager (name, phone, id_account)
            //         VALUES
            //             ('John Doe', '5525259988', 1),
            //             ('John d', '5525259238', 2)
            //         """);
            // insertInfoManager.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}