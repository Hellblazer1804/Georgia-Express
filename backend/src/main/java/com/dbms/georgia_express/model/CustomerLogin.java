package com.dbms.georgia_express.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customerlogin")
public class CustomerLogin {
    @Id
    private String username;

    private String password;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public CustomerLogin() {}

    public CustomerLogin(String username, String password, Customer customer) {
        this.username = username;
        this.password = password;
        this.customer = customer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}