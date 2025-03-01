package com.revature.projects.controller;

import java.sql.SQLException;

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
    public Javalin startAPI(){
        Javalin app = Javalin.create();
        app.post("/accounts/new_manager", this::postManager);
        app.get("/accounts/all_managers", this::getAllManagers);
        app.get("/accounts/manager/{id}", this::getManagerByID);


        System.out.println("END POINTS REGISTRADOS    ");
        return app;
    }
    private void postManager(Context ctx) throws JsonProcessingException, SQLException{
        ObjectMapper mapper = new ObjectMapper();
        Manager manager = mapper.readValue(ctx.body(), Manager.class);
        Manager addManager = managerSERVICES.addManager(manager);

        if(addManager == null){
            ctx.status(400);
        }else{
            ctx.status(201).json(mapper.writeValueAsString(addManager));
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
