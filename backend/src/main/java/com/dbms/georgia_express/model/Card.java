package com.dbms.georgia_express.model;

import jakarta.persistence.*;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int cardId;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "cvv")
    private int cvv;

    @Column(name = "credit_limit")
    private double creditLimit;

    @Column(name = "card_status")
    private String cardStatus;  // e.g., "Active", "Blocked", "Expired"

    @Column(name = "is_approved")
    private boolean approved;

    @Column(name = "verification_reason")
    private String verificationReason;

    @Column(name = "recommended_credit_limit")
    private double recommendedCreditLimit;

    @Column(name = "result")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Card() {}

    public Card(String cardNumber, String cardType, String expiryDate, int cvv,
                double creditLimit, String cardStatus, boolean approved,
                String verificationReason, double recommendedCreditLimit, Customer customer) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.creditLimit = creditLimit;
        this.approved = approved;
        this.verificationReason = verificationReason;
        this.recommendedCreditLimit = recommendedCreditLimit;
        this.customer = customer;
    }

    public Card(boolean approved, String reason, double recommendedCreditLimit) {
        this.approved = approved;
        this.reason = reason;
        this.recommendedCreditLimit = recommendedCreditLimit;
    }

    // Original Card getters and setters
    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
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
}