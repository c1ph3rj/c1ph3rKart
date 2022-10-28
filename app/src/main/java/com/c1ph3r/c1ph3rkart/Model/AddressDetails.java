package com.c1ph3r.c1ph3rkart.Model;


public class AddressDetails {
    private float Id;
    private String name;
    private String houseNo;
    private String streetName;
    private String state;
    private String pinCode;
    private String phoneNumber;


// Getter Methods

    public float getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getState() {
        return state;
    }

    public AddressDetails(float id, String name, String houseNo, String streetName, String state, String pinCode, String phoneNumber) {
        Id = id;
        this.name = name;
        this.houseNo = houseNo;
        this.streetName = streetName;
        this.state = state;
        this.pinCode = pinCode;
        this.phoneNumber = phoneNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

// Setter Methods

    public void setId(float Id) {
        this.Id = Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}