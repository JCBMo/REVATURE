package com.revature.projects.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {

    private String name;
    private Integer age;
    private String phone;
    private Integer salary;
    @JsonProperty("id_account")
    private Integer idAccount;
    @JsonProperty("id_loan")
    private Integer idLoan;
    // private Integer idAddress;
   

    public Customer(){};

    public Customer(String name, Integer age, String phone, Integer salary, Integer idAccount, Integer idLoan ){
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.salary = salary;
        this.idAccount = idAccount;
        this.idLoan = idLoan;
        // this.idAddress = idAddress;
      
        
    }

    public String getName() {
        return name;
    }
    public Integer getAge() {
        return age;
    }
    public String getPhone() {
        return phone;
    }
    public Integer getSalary() {
        return salary;
    }
    public Integer getIdAccount() {
        return idAccount;
    }
    public Integer getIdLoan() {
        return idLoan;
    }
    // public Integer getIdAddress() {
    //     return idAddress;
    // }
  


    //

    public void setName(String name) {
        this.name = name;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setSalary(Integer salary) {
        this.salary = salary;
    }
    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }
    public void setIdLoan(Integer idLoan) {
        this.idLoan = idLoan;
    }
    // public void setIdAddress(Integer idAddress) {
    //     this.idAddress = idAddress;
    // }
  



    @Override
    public String toString() {
        return "Customer{\n" +
                "Name: " + name +"\n" +
                "Age: " + age +"\n" +
                "Phone: " + phone +"\n" +
                "ID Account: " + idAccount +"\n" +
                "ID Loan: " + idLoan;
    }

    

    
}
