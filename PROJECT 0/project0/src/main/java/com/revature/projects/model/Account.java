package com.revature.projects.model;

public class Account {

    private Integer idAccount;
    private String email;
    private String password;
    private Boolean isManager;

    public Account(){};

    public Account(Integer idAccount, String email, String password, Boolean isManager){
        this.idAccount = idAccount;
        this.email = email;
        this.password = password;
        this.isManager = isManager;  
    }

    // GETTERS**
    public Integer getIdAccount() {
        return idAccount;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Boolean getIsManager() {
        return isManager;
    }

    // SETTERS**
    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setIsManager(Boolean isManager) {
        this.isManager = isManager;
    }

    @Override
    public String toString() {
        return "Accoiunt{\n" +
                "ID Account: " + idAccount +"\n" +
                "Email: " + email + "\n" +
                "Password: " + password + "\n" +
                "Type: " + isManager;
    }
}
