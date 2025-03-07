package com.revature.projects.services;

import java.sql.SQLException;
import java.util.List;

import com.revature.projects.dao.CustomerDAO;
import com.revature.projects.model.Customer;

public class CustomerSERVICE {
    CustomerDAO customerDAO;

    public CustomerSERVICE(){}

    public CustomerSERVICE(CustomerDAO customerDAO){
        this.customerDAO = customerDAO;
    }
    
    // ACTIONS***
    public Customer addCustomer(Customer customer, int idAccount) throws SQLException{
        Customer addedCustomer = customerDAO.createCustomer(customer, idAccount);

        if(addedCustomer == null){
            throw new RuntimeException("Cant add Customer (service)");
        }
        return addedCustomer;
    }

    public List<Customer> getAllCustomers() throws SQLException{
        return customerDAO.getAllCustomers();
    }


    public boolean updateCustomer(int idAccount, Integer age, String phone, Integer salary) throws SQLException{
        if(age == null || phone == null || salary == null){
            throw new IllegalArgumentException("Cant be Null (CustoerSERVICES) " );
        }
        return customerDAO.updateCustomer(idAccount, age, phone, salary);
    }
}
