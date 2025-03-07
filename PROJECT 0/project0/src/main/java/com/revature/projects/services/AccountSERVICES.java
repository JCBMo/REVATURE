package com.revature.projects.services;

import java.sql.SQLException;
import java.util.List;

import com.revature.projects.dao.AccountDAO;
import com.revature.projects.model.Account;

public class AccountSERVICES {

    AccountDAO accountDAO;

    public AccountSERVICES(){}   

    public AccountSERVICES(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    // ACTIONS***
    public Account authenticate(String email, String password) throws SQLException{
        Account account = accountDAO.loginAccount(email);
        if(account != null && account.getPassword().equals(password)){
            return account;
        }else{
            return null;
        }
    }

    public Account addAccount(Account account) throws SQLException{
        Account addedAccount = accountDAO.createAccount(account);
        if(addedAccount == null){
            throw new RuntimeException("IMPOSIBLE add Account");
        }
        return addedAccount;
    }

    public List<Account> getAllAccounts() throws SQLException{
        return accountDAO.getAllAccounts();
    }

    public Account getAccountByID(Integer idAccount) throws SQLException{
        return accountDAO.getAccountByID(idAccount);
    }

    public boolean updateAccount(int idAccount, String email, String password) throws SQLException{
        if (email == null || password == null) {
            throw new IllegalArgumentException("Cant be NULL (AccountSERVICES)");
        }
        return accountDAO.updateAccount(idAccount, email, password);    
    }
}