package com.revature.projects.model;

public class Address {
    private Integer idAddress;
    private String state;
    private String city;
    private Integer zip;
    private String street;

    public Address(){}

    public Address(Integer idAddress, String state, String city, Integer zip, String street){
        this.idAddress = idAddress;
        this.state = state;
        this.city = city;
        this.zip = zip;
        this.street = street;
    }

    public Integer getIdAddress() {
        return idAddress;
    }
    public String getState() {
        return state;
    }
    public String getCity() {
        return city;
    }
    public Integer getZip() {
        return zip;
    }
    public String getStreet() {
        return street;
    }


    public void setIdAddress(Integer idAddress) {
        this.idAddress = idAddress;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setZip(Integer zip) {
        this.zip = zip;
    }
    public void setStreet(String street) {
        this.street = street;
    }
}
