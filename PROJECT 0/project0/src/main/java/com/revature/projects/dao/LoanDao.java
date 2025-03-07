package com.revature.projects.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.DataBaseConnection;
import com.revature.projects.model.Loan;

public class LoanDao {


     public Loan createLoan(Loan newLoan) throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        String sql = "INSERT INTO Loan (amount, status, aplication_date, id_account) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
            
                stmt.setInt(1, newLoan.getAmount());
                stmt.setString(2, newLoan.getStatus());
                stmt.setDate(3, Date.valueOf(newLoan.getAplicationDate()));
                stmt.setInt(4, newLoan.getIdAccount());
    
                stmt.executeUpdate();
        
            try(ResultSet generetedKeys = stmt.getGeneratedKeys()){
                if(generetedKeys.next()){
                    int newID = generetedKeys.getInt(1);
                    newLoan.setIdLoan(newID);
                }else {
                    throw new SQLException("Cant get the genereted ID");
                }
            }
        } finally{
            if(connection != null){
                connection.close();
            }
        }
        return newLoan;
    }

    public List<Loan> getAllLoans() throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        List<Loan> allLoans = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Loan";
            PreparedStatement prepStmt = connection.prepareStatement(sql);

            ResultSet rs = prepStmt.executeQuery();

            while (rs.next()) {
                allLoans.add(new Loan(
                rs.getInt("id_loan"),
                rs.getInt("amount"),
                rs.getString("status"), 
                rs.getString("aplication_date"),
                rs.getInt("id_account")));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allLoans;
    }

    public List<Loan> getLoansByAccount(int idAccount) throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        List<Loan> loans = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Loan WHERE id_account = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idAccount);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                loans.add(new Loan(
                rs.getInt("id_loan"),
                rs.getInt("amount"),
                rs.getString("status"), 
                rs.getString("aplication_date"),
                rs.getInt("id_account")));
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return loans;
        }


    public Loan getLoanByID(int id) throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        String sql = "SELECT * FROM Account WHERE id_loan = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            

            while (rs.next()) {
                Loan loan = new Loan(rs.getInt("id_loan"),
                        rs.getInt("amount"),
                        rs.getString("status"),
                        rs.getString("aplication_date"),
                        rs.getInt("id_account"));
                return loan;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateLoan(int idLoan, String status) throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        String sql = "UPDATE Loan SET status = ? WHERE id_loan = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, idLoan);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Cant Update Loan (LoanDAO) " + e.getMessage());
            return false;
        }finally{
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Cant Close conexion (LoantDAO)" + e.getMessage());
            }
        }
    }
}
