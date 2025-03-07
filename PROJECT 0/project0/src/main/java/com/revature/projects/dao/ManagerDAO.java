package com.revature.projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.DataBaseConnection;
import com.revature.projects.model.Manager;

public class ManagerDAO {

        public Manager createManager(Manager newManager) throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        String sql = "INSERT INTO Manager (name, phone, id_account) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
            
                stmt.setString(1, newManager.getName());
                stmt.setString(2, newManager.getPhone());
                stmt.setInt(3, newManager.getIdAccount());
    
                stmt.executeUpdate();
        
            try(ResultSet generetedKeys = stmt.getGeneratedKeys()){
                if(generetedKeys.next()){
                    int newID = generetedKeys.getInt(1);
                    newManager.setIdManager(newID);
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
        return newManager;
    }


       public List<Manager> getAllManagers() throws SQLException{
        
        List<Manager> managerS = new ArrayList<>(); 
        try (Connection connection = DataBaseConnection.getConnection();){
            String sql = "SELECT * FROM Manager";
            PreparedStatement prepStmt = connection.prepareStatement(sql);

            ResultSet rs = prepStmt.executeQuery();

            while (rs.next()) {
                managerS.add(new Manager(
                rs.getInt("id_manager"),
                rs.getString("name"),
                rs.getString("phone"), 
                rs.getInt("id_account")));

            }
            
        } catch (SQLException e) {
            throw new SQLException("MANAGERS NOT FOUND: " + e.getMessage(), e);
        }
        return managerS;
    }


    public Manager getAccountByID(int id) throws SQLException{
        Connection connection = DataBaseConnection.getConnection();
        String sql = "SELECT * FROM Manager WHERE id_manager = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            

            while (rs.next()) {
                Manager manager = new Manager(rs.getInt("id_manager"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getInt("id_account"));
                return manager;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
