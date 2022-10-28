package com.c1ph3r.c1ph3rkart.Model;


public class userDetail {
    private String Id;
    private String userName;
    private String password;
    private String eMail;
    private String phoneNumber;
    private String cart;
    private String orderDetails;

    public userDetail(String id, String userName, String password, String eMail, String phoneNumber, String cart, String orderDetails) {
        this.Id = id;
        this.userName = userName;
        this.password = password;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.cart = cart;
        this.orderDetails = orderDetails;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }


// Getter Methods

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

// Setter Methods

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}