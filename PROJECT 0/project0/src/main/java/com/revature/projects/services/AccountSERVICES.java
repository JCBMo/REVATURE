package com.revature.projects.services;

import java.sql.SQLException;
import java.util.List;

import com.revature.projects.dao.AccountDAO;
import com.revature.projects.model.Account;

public class AccountSERVICES {

    AccountDAO accountDAO;

    public AccountSERVICES(){

    }

    public AccountSERVICES(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }


    // ACTIONS***
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

    public Account getAccountByID(Integer id_account) throws SQLException{
        return accountDAO.getAccountByID(id_account);

    }

}