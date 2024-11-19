package com.dbms.georgia_express.dto;

import com.dbms.georgia_express.model.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class CardDTO {

    @JsonProperty("cardNumber")
    private String cardNumber;


    @JsonProperty("expirationDate")
    private String expiryDate;

    @JsonProperty("cvv")
    private int cvv;

    @JsonProperty("creditLimit")
    private double creditLimit;

    @JsonProperty("cardStatus")
    private String cardStatus;  // e.g., "Active", "Blocked", "Expired"

    @JsonProperty("isApproved")
    private boolean approved;

    @JsonProperty("verificationReason")
    private String verificationReason;

    @JsonProperty("recommendedCreditLimit")
    private double recommendedCreditLimit;

    @JsonProperty("result")
    private String reason;

    @ManyToOne
    @JsonProperty("customer_id")
    private Customer customer;

    @JsonProperty("cardBalance")
    private BigDecimal cardBalance;

    @JsonProperty("minimumPayment")
    private BigDecimal minimumPayment;

    @JsonProperty("rewardPoints")
    private int rewardPoints;


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // VerificationResult getters and setters
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getVerificationReason() {
        return verificationReason;
    }

    public void setVerificationReason(String verificationReason) {
        this.verificationReason = verificationReason;
    }

    public double getRecommendedCreditLimit() {
        return recommendedCreditLimit;
    }

    public void setRecommendedCreditLimit(double recommendedCreditLimit) {
        this.recommendedCreditLimit = recommendedCreditLimit;
    }

    public String getReason() {
        return reason;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public BigDecimal getMinimumPayment() {
        return minimumPayment;
    }

    public void setMinimumPayment(BigDecimal minimumPayment) {
        this.minimumPayment = minimumPayment;
    }

    public BigDecimal getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(BigDecimal cardBalance) {
        this.cardBalance = cardBalance;
    }
}
