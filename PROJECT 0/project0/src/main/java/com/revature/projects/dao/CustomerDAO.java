package com.revature.projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.DataBaseConnection;
import com.revature.projects.model.Customer;

public class CustomerDAO {
    
    public Customer createCustomer(Customer newCustomer, int idAccount) throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        String sql = "INSERT INTO Customer (name, age, phone, salary, id_account, id_loan) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
            
                stmt.setString(1, newCustomer.getName());
                stmt.setInt(2, newCustomer.getAge());
                stmt.setString(3, newCustomer.getPhone());
                stmt.setInt(4, newCustomer.getSalary());
                stmt.setInt(5, idAccount);
                stmt.setInt(6, newCustomer.getIdLoan());
    
                stmt.executeUpdate();
        

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if (connection != null) {
                connection.close();
            }

        }
        return newCustomer;
    }




    public List<Customer> getAllCustomers() throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        List<Customer> allCustomers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Customer";
            PreparedStatement prepStmt = connection.prepareStatement(sql);

            ResultSet rs = prepStmt.executeQuery();
         

            while (rs.next()) {
                allCustomers.add(new Customer(
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("phone"), 
                rs.getInt("salary"),
                rs.getInt("id_account"),
                rs.getInt("id_loan")));

            }

        } catch (SQLException e) {
            throw new SQLException("Cant get all customers (Dao :) )" + e.getMessage());
        }finally{
            if ((connection != null)) {
                connection.close();
            }
        }
        return allCustomers;
    }




    public boolean updateCustomer(int idAccount, Integer age, String phone, Integer salary) throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        String sql = "UPDATE Customer SET age = ?, phone = ?, salary = ? WHERE id_account = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, age);
            stmt.setString(2, phone);
            stmt.setInt(3, salary);
            stmt.setInt(4, idAccount);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Cant update Customer (CustomerDAO)  :(  " + e.getMessage());
            return false;
        }finally{
            if ( connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Cant Close conexion (CustomerDAO)" + e.getMessage());
                }
            }
        }
    }
    
}
