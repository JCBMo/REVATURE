package com.revature.projects.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Loan {
    private Integer idLoan;
    private Integer amount;
    private String status;
    @JsonProperty("aplication_date")
    private String aplicationDate;
    @JsonProperty("id_account") 
    private Integer idAccount; 
    

    public Loan(){}

    public Loan(Integer idLoan, Integer amount, String status, String aplicationDate, Integer idAccount){
        this.idLoan = idLoan;
        this.amount = amount;
        this.status = status;
        this.aplicationDate = aplicationDate;
        this.idAccount = idAccount;
    }

    public Integer getIdLoan() {
        return idLoan;
    }
    public Integer getAmount() {
        return amount;
    }
    public String getStatus() {
        return status;
    }
    public String getAplicationDate() {
        return aplicationDate;
    }
    public Integer getIdAccount() {
        return idAccount;
    }


    public void setIdLoan(Integer idLoan) {
        this.idLoan = idLoan;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setAplicationDate(String aplicationDate) {
        this.aplicationDate = aplicationDate;
    }
    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    @Override
    public String toString() {
        return "Loan{\n" +
                "ID Loan: " + idLoan +"\n" +
                "Amount: " + amount + "\n" +
                "Status: " + status + "\n" +
                "Aplication Date: " + aplicationDate + "\n" +
                "ID Account: " + idAccount;
    }
}
