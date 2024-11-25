package com.dbms.georgia_express.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionRequest {

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("cvv")
    private int cvv;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
}
