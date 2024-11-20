package com.dbms.georgia_express.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class PaymentDTO {

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("payment_amount")
    private BigDecimal paymentAmount;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("verification_reason")
    private String verificationReason;

    // Getters and setters

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getVerificationReason() {
        return verificationReason;
    }

    public void setVerificationReason(String verificationReason) {
        this.verificationReason = verificationReason;
    }
}

