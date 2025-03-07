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


    public Account loginAccount(String email) throws SQLException{
       
        Connection connection = DataBaseConnection.getConnection();
        String sql = "SELECT * FROM Account WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return new Account(
                    rs.getInt("id_account"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getBoolean("is_manager")
                );
                
            }else{
                return null;
            }
            
        }finally{
            if (connection != null) {
                connection.close();
            }
        }
    }


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
                }else {
                    throw new SQLException("Cant get the genereted ID");
                }
            }
        } finally{
            if(connection != null){
                connection.close();
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


    public boolean  updateAccount(int idAccount, String email, String password) throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        String sql = "UPDATE Account SET email = ?, password = ? WHERE id_account = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql) ){
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setInt(3, idAccount);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Cant update Account (AcountDAO)  :(  " + e.getMessage());
            return false;
        }finally{
            if ( connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Cant Close conexion (AccountDAO)" + e.getMessage());
                }
            }
        }
    }

}
