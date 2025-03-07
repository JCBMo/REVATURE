package com.revature.projects.services;

import java.sql.SQLException;
import java.util.List;

import com.revature.projects.dao.ManagerDAO;
import com.revature.projects.model.Manager;


public class ManagerSERVICES {

    ManagerDAO managerDAO;

    public ManagerSERVICES(){};

    public ManagerSERVICES(ManagerDAO managerDAO){
        this.managerDAO = managerDAO;
    }

     // ACTIONS***
    public Manager addManager(Manager manager) throws SQLException{
        
        Manager addedManager = managerDAO.createManager(manager);
        if(addedManager == null){
            throw new RuntimeException("IMPOSIBLE add Account");
        }
        return addedManager;
    }

    public List<Manager> getAllManagers() throws SQLException{
        return managerDAO.getAllManagers();
    }

    public Manager getManagerByID(Integer id_account) throws SQLException{
        return managerDAO.getAccountByID(id_account);

    }



}
