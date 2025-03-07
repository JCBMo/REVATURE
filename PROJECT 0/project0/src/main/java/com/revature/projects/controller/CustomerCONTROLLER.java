package com.revature.projects.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.model.Account;
import com.revature.projects.model.Customer;
import com.revature.projects.services.CustomerSERVICE;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class CustomerCONTROLLER {
    
    private CustomerSERVICE customerSERVICE;

    public CustomerCONTROLLER(CustomerSERVICE customerSERVICE){
        this.customerSERVICE = customerSERVICE;
    }


    // ENDPOINTS 
    public void addRoutes(Javalin app){
        app.post("/customer", this::postCustomer);
        app.get("/customers", this::getAllCustomers);
        app.put("/customer/{id}", this::updateCustomer);
    }

    private void postCustomer(Context ctx) throws JsonProcessingException, SQLException{
        try {
            Account currentSession = ctx.sessionAttribute("user");
            if(currentSession == null){
                ctx.status(401).result("First Login (LoanCONTROLLER)");
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            Customer customer = mapper.readValue(ctx.body(), Customer.class);
            int idAccount = customer.getIdAccount();

            if(!currentSession.getIsManager() || idAccount != currentSession.getIdAccount()){
                ctx.status(403).result("Acces denied (LoanCONTROLLER)");
                return;
            }
            Customer addedCustomer = customerSERVICE.addCustomer(customer, idAccount);

            if (addedCustomer == null) {
                ctx.status(400).json(Map.of("error", "Cant add customer (CustomerCONTROLLER)"));
            } else {
                ctx.status(200).json(addedCustomer);
            }

        } catch (Exception e) {
            ctx.status(500).result("Something went wrong (CustomerCONTROLLER): " + e.getMessage());
        }
    }


    private void getAllCustomers(Context ctx) throws SQLException{
        try {
            Account currentSession = ctx.sessionAttribute("user");
            if(currentSession == null){
                ctx.status(401).result("First Login (LoanCONTROLLER)");
                return;
            }

            // 
            if(!currentSession.getIsManager()){
                ctx.status(403).result("Acces denied (LoanCONTROLLER)");
                return;
            }
            List<Customer> customers = customerSERVICE.getAllCustomers();
            ctx.json(customers);

        } catch (RuntimeException e) {
            ctx.status(500).result("Cant get all Customers (controller)" + e.getMessage());
        }
    }



    private void updateCustomer(Context ctx ){
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
            int idCustomer = Integer.parseInt(ctx.pathParam("id"));
            ObjectMapper mapper = new ObjectMapper();
            Customer customer = mapper.readValue(ctx.body(), Customer.class);

            if(!currentSession.getIsManager() || idAccount != currentSession.getIdAccount()){
                ctx.status(403).result("Acces denied (LoanCONTROLLER)");
                return;
            }

            boolean isUpdated = customerSERVICE.updateCustomer(idCustomer, customer.getAge(), customer.getPhone(), customer.getSalary());

            if (isUpdated) {
                ctx.status(200).result("Customer Updated (CustomerCONTROLLER)");
            } else {
                ctx.status(404).result("Customer cant be found (CustomerCONTROLLER)");
            }
        } catch (Exception e) {
            ctx.status(400).result("Cant Update Customer (CustomerCONTROLLER)" + e.getMessage());
        }
    }
}
