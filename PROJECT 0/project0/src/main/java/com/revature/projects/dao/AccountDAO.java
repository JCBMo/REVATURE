package com.revature.projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.DataBaseConnection;
import com.revature.projects.model.Account;

public class AccountDAO {


    public Account createAccount(Account newAccount) throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        String sql = "INSERT INTO Account (email, password, is_manager) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
            
                stmt.setString(1, newAccount.getEmail());
                stmt.setString(2, newAccount.getPassword());
                stmt.setBoolean(3, newAccount.getIsManager());
    
                stmt.executeUpdate();
        
            try(ResultSet generetedKeys = stmt.getGeneratedKeys()){
                if(generetedKeys.next()){
                    int newID = generetedKeys.getInt(1);
                    newAccount.setIdAccount(newID);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión si ya no se necesita
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return newAccount;
    }

  

    public List<Account> getAllAccounts() throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        List<Account> accountS = new ArrayList<>(); 
        try {
            String sql = "SELECT * FROM Account";
            PreparedStatement prepStmt = connection.prepareStatement(sql);

            ResultSet rs = prepStmt.executeQuery();

            while (rs.next()) {
                accountS.add(new Account(
                rs.getInt("id_account"),
                rs.getString("email"),
                rs.getString("password"), 
                rs.getBoolean("is_manager")));
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return accountS;
    }

    public List<Account> getAllManagerAccounts(boolean isManager) throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        List<Account> managerAccounts = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Account WHERE isManager = TRUE";
            PreparedStatement prepStmt = connection.prepareStatement(sql);

            ResultSet rs = prepStmt.executeQuery();

            while (rs.next()) {
                managerAccounts.add(new Account(
                rs.getInt("idAccount"),
                rs.getString("email"),
                rs.getString("password"), 
                rs.getBoolean("isManager")));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return managerAccounts;
    }

    public Account getAccountByID(int id) throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        String sql = "SELECT * FROM Account WHERE id_account = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            

            while (rs.next()) {
                Account account = new Account(rs.getInt("id_account"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("is_manager"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
