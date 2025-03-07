package com.revature.projects.services;

import java.sql.SQLException;
import java.util.List;

import com.revature.projects.dao.LoanDao;
import com.revature.projects.model.Loan;

public class LoanSERVICES {

    LoanDao loanDao;

    public LoanSERVICES(){}

    public LoanSERVICES(LoanDao loanDao){
        this.loanDao = loanDao;
    }
    

    // ACTIONS***
    public Loan addLoan(Loan loan) throws SQLException{
        Loan addedLoan = loanDao.createLoan(loan);

        if(addedLoan == null){
            throw new RuntimeException("IMPOSIBLE add Loan");
        }
        return addedLoan;
    }

    public List<Loan> getAllLoans() throws SQLException{
        return loanDao.getAllLoans();
    }

    public  Loan getLoanByID(Integer idLoan) throws SQLException{
        return loanDao.getLoanByID(idLoan);
    }

    public List<Loan> getLoansByAccount(int idAccount) throws SQLException{
        return loanDao.getLoansByAccount(idAccount);
    }

    public boolean updateLoan(int idLoan, String status) throws SQLException{
        if( status == null ){
            throw new IllegalArgumentException("Cant be NULL (LoanSERVICES");
        }
        return loanDao.updateLoan(idLoan, status);
    }
}
