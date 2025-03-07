package com.revature.projects.controller;

import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.model.Account;
import com.revature.projects.model.Loan;
import com.revature.projects.services.LoanSERVICES;

import io.javalin.Javalin;
import io.javalin.http.Context;


public class LoanCONTROLLER {

    private LoanSERVICES loanSERVICES;

    public LoanCONTROLLER(LoanSERVICES loanSERVICES){
        this.loanSERVICES = loanSERVICES;
    }


    // ENDPOINTS 
    public void addRoutes(Javalin app){
        app.post("/loan", this::postLoan);
        app.get("/loans", this::getAllLoans);
        app.put("/loan/{id}", this::updateLoan);
    }

    private void postLoan(Context ctx) throws JsonProcessingException, SQLException{
        try {
            Account currentSession = ctx.sessionAttribute("user");
            if(currentSession == null){
                ctx.status(401).result("First Login (LoanCONTROLLER)");
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            Loan loan = mapper.readValue(ctx.body(), Loan.class);

            if(loan.getIdAccount() != currentSession.getIdAccount() || !currentSession.getIsManager()){
                ctx.status(403).result("Acces denied (LoanCONTROLLER)");
                return;
            }

            Loan addloan = loanSERVICES.addLoan(loan);
            if (addloan == null) {
                ctx.status(400).result("Cant create Loan (LoanCONTROLLER)");
            }else{
                ctx.status(200).result("Loan register (LoanCONTROLLER)");
            }
        } catch (Exception e) {
            ctx.status(500).result("Something went wrong (LoanCONTROLLER)");
        }
    }

    private void getAllLoans(Context ctx) throws SQLException{
        try {
            Account currentSession = ctx.sessionAttribute("user");
            if(currentSession == null){
                ctx.status(401).result("First Login (LoanCONTROLLER)");
                return;
            }

            List<Loan> loans;
            if(currentSession.getIsManager()){
                loans = loanSERVICES.getAllLoans();
            }else{
                loans = loanSERVICES.getLoansByAccount(currentSession.getIdAccount());
            }
            if (loans.size() == 0) {
                ctx.status(200).result("Loans empty");
                return;
            }
            ctx.json(loans);
        } catch (RuntimeException e) {
            ctx.status(500).result("Something went wrong (LoanCONTROLLER)");
        }
    }

    private void updateLoan(Context ctx){
        try {
            int LoanToUpdate = Integer.parseInt(ctx.pathParam("id"));

            Account currentSession = ctx.sessionAttribute("user");

            if(currentSession == null){
                ctx.status(401).result("First Login (LoanCONTROLLER)");
                return;
            }

            Loan idLoan = loanSERVICES.getLoanByID(LoanToUpdate);

            if(idLoan == null){
                ctx.status(404).result("ID Loan Dosnt exist (LoanCONTROLLER)");
                return;
            }

            if(idLoan.getIdAccount() != currentSession.getIdAccount() || !currentSession.getIsManager()){
                ctx.status(403).result("Acces Denied (LoanCONTROLLER)");
            }

            Loan updatesToLoan = ctx.bodyAsClass(Loan.class);

            boolean nowUpdate = loanSERVICES.updateLoan(LoanToUpdate, updatesToLoan.getStatus());

            if(nowUpdate){
                ctx.status(200).result("Loan Update (LoanCONTROLLER)");
            }else{
                ctx.status(500).result("Cant be Update (LoanCONTROLLER)");
            }

        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid ID (LoanCONTROLLER)");
        } catch(Exception e){
            ctx.status(500).result("Error detected (LoanCONTROLLER)" + e.getMessage());
        }
    }

}
