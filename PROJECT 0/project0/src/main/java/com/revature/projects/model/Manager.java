package com.revature.projects.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Manager {

    private Integer idManager;
    private String name;
    private String phone;

    @JsonProperty("id_account")
    private Integer idAccount;
    

    public Manager(){};

    public Manager(Integer idManager, String name, String phone, Integer idAccount){
        this.idManager = idManager;
        this.name = name;
        this.phone = phone;
        this.idAccount = idAccount;
    }

    public Integer getIdAccount() {
        return idAccount;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public Integer getIdManager() {
        return idManager;
    }


    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setIdManager(Integer idManager) {
        this.idManager = idManager;
    }

    @Override
    public String toString() {
        return "Manager{\n" +
                "ID Manager: " + idManager + "\n" +
                "Email: " + name + "\n" +
                "Password: " + phone +"\n" +
                "ID Account: " + idAccount;
    }
}
