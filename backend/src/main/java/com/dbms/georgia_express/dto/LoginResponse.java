package com.dbms.georgia_express.dto;

import com.dbms.georgia_express.model.Customer;
import com.dbms.georgia_express.model.CustomerLogin;

public class LoginResponse {
    private String token;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private Customer customer;

    public LoginResponse(String token, Customer customer) {
        this.token = token;
        this.customer = customer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}