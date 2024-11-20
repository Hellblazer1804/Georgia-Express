package com.dbms.georgia_express.dto;

import com.dbms.georgia_express.model.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransactionDTO {
    @JsonProperty("transaction_id")
    private Long transactionId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("transaction_date")
    private String transactionDate;

    public TransactionDTO(Transaction transaction) {
        this.transactionId = transaction.getId();
        this.username = transaction.getCustomerLogin().getUsername();
        this.amount = transaction.getAmount();
        this.transactionDate = transaction.getTransactionDate();
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
