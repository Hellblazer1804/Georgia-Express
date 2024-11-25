package com.dbms.georgia_express.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionRequest {
    @JsonProperty("username")
    private String username;

    @JsonProperty("card_number")
    private String cardNumber;

    public String getUsername() {
        return username;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
