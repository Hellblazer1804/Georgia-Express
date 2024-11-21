package com.dbms.georgia_express.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionResponse {
    @JsonProperty("message")
    private String message;

    private TransactionDTO transaction;

    public TransactionResponse(String message, TransactionDTO transaction) {
        this.message = message;
        this.transaction = transaction;
    }

    public TransactionDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionDTO transaction) {
        this.transaction = transaction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
