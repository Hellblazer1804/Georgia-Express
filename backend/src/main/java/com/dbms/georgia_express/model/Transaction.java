package com.dbms.georgia_express.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(name="transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private CustomerLogin customerLogin;

    @Column(name="amount",nullable = false)
    private BigDecimal amount;

    @Column(name="transaction_date", nullable = false)
    private String transactionDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerLogin getCustomerLogin() {
        return customerLogin;
    }

    public void setCustomerLogin(CustomerLogin customerLogin) {
        this.customerLogin = customerLogin;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
