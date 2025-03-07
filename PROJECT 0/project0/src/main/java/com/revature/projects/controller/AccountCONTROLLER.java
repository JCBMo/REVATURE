package com.revature.projects.controller;


import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

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

    public void addRoutes(Javalin app){
        app.post("/account", this::postAccount);
        app.get("/accounts", this::getAllAccounts);
        app.get("/account/{id}", this::getAccountByID);
        app.put("/account/{id}", this::updateAccount);
        app.post("/login", this::loginAccount);
        app.post("/logout", this::logOut);
  
    } 


    // ACTIONS***

    private void loginAccount(Context ctx){
        try {

            Account loginReq = ctx.bodyAsClass(Account.class);

            Account account = accountSERVICES.authenticate(loginReq.getEmail(), loginReq.getPassword());

            if(account != null){
                ctx.sessionAttribute("user", account);
                ctx.status(200).result("Correct LogIn (AccountCONTROLLER)");
            }else{
                ctx.status(400).result("Bad Credentials (AccountCONTROLLER)" );
            }
        } catch (Exception e) {
            ctx.status(500).result("Cant LogIn, server error (AccountCONTROLLER) " + e.getMessage());
        }
    }


    private void logOut(Context ctx) throws Exception {
        ctx.sessionAttribute("user", null);
        ctx.req().getSession().invalidate();
        ctx.status(200).result("Sesión closed (AccountController)");
    }


    private void postAccount(Context ctx) throws JsonProcessingException, SQLException{
        try {
            Account currentSession = ctx.sessionAttribute("user");
            if(currentSession == null){
                ctx.status(401).result("First Login (AccountCONTROLLER)");
                return;
            }
            if (!currentSession.getIsManager()) {
                ctx.status(403).result("Action reserved for Managers (AccountCONTROLLER)");
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(ctx.body(), Account.class);

            String hashPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
            account.setPassword(hashPassword);

            Account addAccount = accountSERVICES.addAccount(account);

            if(addAccount == null){
                ctx.status(400).result("Cant register new account (AccountCONTROLLER)");
            }else{
                ctx.status(201).json(mapper.writeValueAsString(addAccount));
            }
        } catch (Exception e) {
            ctx.status(500).result("Something went wrong (AccontCONTROLLER)");
        }
    }


    private void getAllAccounts(Context ctx) throws SQLException{
        try {
            
            Account currentSession = ctx.sessionAttribute("user");
            if(currentSession == null){
                ctx.status(401).result("First Login (AccountCONTROLLER)");
                return;
            }
            if(!currentSession.getIsManager()){
                ctx.status(403).result("Action reserved for Managers (AccountCONTROLLER)");
                return;
            }
            System.out.println(accountSERVICES.getAllAccounts());
            ctx.json(accountSERVICES.getAllAccounts());
            
        } catch(Exception e){
            ctx.status(500).result("Cant show accounts (AccountCONTROLLER)" + e.getMessage()) ;
        }
    }


    private void getAccountByID(Context ctx) throws SQLException{
        try {
            Account currentSession = ctx.sessionAttribute("user");
            if(currentSession == null){
                ctx.status(401).result("First Login (AccountCONTROLLER)");
                return;
            }
            Integer idManager =Integer.parseInt(ctx.pathParam("id"));
            if(!currentSession.getIsManager() && currentSession.getIdAccount() != idManager){
                ctx.status(403).result("Action reserved for Managers (AccountCONTROLLER)");
                return;
            }
            
            //System.out.println(idManager);
            Account account = accountSERVICES.getAccountByID(idManager);
            ctx.json(account);

        } catch (Exception e) {
            ctx.status(500).result("Cant get account by id (AccountCONTROLLER)" + e.getMessage()) ;
        }
        
    }


    private void updateAccount(Context ctx ){
        try {
            int idAccount = Integer.parseInt(ctx.pathParam("id"));
            Account currentSession = ctx.sessionAttribute("user");
            if(currentSession == null){
                ctx.status(401).result("First Login (AccountCONTROLLER)");
                return;
            }
            if(currentSession.getIdAccount() != idAccount){
                ctx.status(403).result("Dont have access (AccountCONTROLLER)");
                return;
            }
            Account account = ctx.bodyAsClass(Account.class);
            boolean accountUpdate = accountSERVICES.updateAccount(idAccount, account.getEmail(), account.getPassword());
            if(accountUpdate){
                ctx.status(200).result("Account Update (AccountCONTROLLER)");
            }else{
                ctx.status(404).result("Account cant be found (AccountCONTROLLER)");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid ID (AccountCONTROLLER)");
        }catch(Exception e){
            ctx.status(500).result("Cant be uppdate (AccountCONTROLLER)" + e.getMessage()) ;
        }
    }   
}