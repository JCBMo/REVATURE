package com.revature.projects.controller;

import java.sql.SQLException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.model.Manager;
import com.revature.projects.services.ManagerSERVICES;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class ManagerCONTROLLER {
    
    private ManagerSERVICES managerSERVICES;

    public ManagerCONTROLLER(ManagerSERVICES managerSERVICES){
        this.managerSERVICES = managerSERVICES;
    }


    //ENDPOINTS***
    public void addRoutes(Javalin app){
        app.post("/account/manager", this::postManager);
        app.get("/account/managers", this::getAllManagers);
        app.get("/account/manager/{id}", this::getManagerByID);
    }

    private void postManager(Context ctx) throws JsonProcessingException, SQLException{
        ObjectMapper mapper = new ObjectMapper();
        Manager manager = mapper.readValue(ctx.body(), Manager.class);
        Manager addManager = managerSERVICES.addManager(manager);

        try {
            if(addManager == null){
                ctx.status(400).json(Map.of("ERROR: ", "CANT CREATE MANAGER"));
            }else{
                ctx.status(201).json(addManager);
            }
        } catch (Exception e) {
        }
    }

    private void getAllManagers(Context ctx) throws SQLException{
        try {
            System.out.println(managerSERVICES.getAllManagers());
            ctx.json(managerSERVICES.getAllManagers());
        } catch (RuntimeException e) {
            ctx.status(500).result("CANT GET MANAGER: " + e.getMessage());
        }
    }

    private void getManagerByID(Context ctx) throws SQLException{
        Integer idManager =Integer.parseInt(ctx.pathParam("id"));
        System.out.println(idManager);
        Manager manager = managerSERVICES.getManagerByID(idManager);
        ctx.json(manager);
    }
}
