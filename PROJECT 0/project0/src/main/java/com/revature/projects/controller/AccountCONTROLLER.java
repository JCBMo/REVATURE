package com.revature.projects.controller;


import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.model.Account;
import com.revature.projects.services.AccountSERVICES;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class AccountCONTROLLER {
    private AccountSERVICES accountSERVICES;

    public AccountCONTROLLER(AccountSERVICES accountSERVICES) {
        this.accountSERVICES = accountSERVICES;
    }

    public Javalin startAPI(){
        Javalin app = Javalin.create();
        app.post("/accounts", this::postAccount);
        app.get("/accounts", this::getAllAccounts);
        app.get("/accounts/{id}", this::getAccountByID);


        System.out.println("END POINTS REGISTRADOS    ");
        return app;
    }   


    // ACTIONS***
    private void postAccount(Context ctx) throws JsonProcessingException, SQLException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addAccount = accountSERVICES.addAccount(account);

        if(addAccount == null){
            ctx.status(400);
        }else{
            ctx.status(201).json(mapper.writeValueAsString(addAccount));
        }
    }

    private void getAllAccounts(Context ctx) throws SQLException{
        try {
            System.out.println(accountSERVICES.getAllAccounts());
            ctx.json(accountSERVICES.getAllAccounts());
        } catch (RuntimeException e) {
            ctx.status(500).result("CANT GET ACCOUNTS: " + e.getMessage());
        }
    }

 

    private void getAccountByID(Context ctx) throws SQLException{
        Integer idManager =Integer.parseInt(ctx.pathParam("id"));
        System.out.println(idManager);
        Account account = accountSERVICES.getAccountByID(idManager);
        ctx.json(account);

    }
   
}