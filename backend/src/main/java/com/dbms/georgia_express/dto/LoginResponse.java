package com.dbms.georgia_express.dto;

import com.dbms.georgia_express.model.Customer;
import com.dbms.georgia_express.model.CustomerLogin;

public class LoginResponse {
    private String token;

    private int customerId;

    public LoginResponse(String token, int customerId) {
        this.token = token;
        this.customerId = customerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

}