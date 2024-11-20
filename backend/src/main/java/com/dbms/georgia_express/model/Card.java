package com.dbms.georgia_express.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @Column(name = "card_number")
    private String cardNumber;


    @Column(name = "expiration_date")
    private String expiryDate;

    @Column(name = "cvv")
    private int cvv;

    @Column(name = "credit_limit", columnDefinition = "NUMERIC(12,2)")
    private double creditLimit;

    @Column(name = "card_status")
    private String cardStatus;  // e.g., "Active", "Blocked", "Expired"

    @Column(name = "is_approved")
    private boolean approved;

    @Column(name = "verification_reason")
    private String verificationReason;

    @Column(name = "recommended_credit_limit")
    private double recommendedCreditLimit;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "card_balance", columnDefinition = "NUMERIC(12,2)")
    private BigDecimal cardBalance;

    @Column(name = "minimum_payment", columnDefinition = "NUMERIC(8,2)")
    private BigDecimal minimumPayment;

    @Column(name = "reward_points", columnDefinition = "NUMERIC(10,2)")
    private int rewardPoints;

    public Card() {}

    public Card(String cardNumber, String expiryDate, int cvv,
                double creditLimit, String cardStatus, boolean approved,
                String verificationReason, double recommendedCreditLimit, Customer customer,
                BigDecimal cardBalance, BigDecimal minimumPayment,
                int rewardPoints) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.creditLimit = creditLimit;
        this.approved = approved;
        this.verificationReason = verificationReason;
        this.recommendedCreditLimit = recommendedCreditLimit;
        this.customer = customer;
        this.cardBalance = cardBalance;
        this.minimumPayment = minimumPayment;
        this.rewardPoints = rewardPoints;
        this.cardStatus = cardStatus;
    }

    public Card(boolean approved, double recommendedCreditLimit) {
        this.approved = approved;
//        this.reason = reason;
        this.recommendedCreditLimit = recommendedCreditLimit;
    }

    // Original Card getters and setters

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

    public String getCardStatus(){
        return  cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
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