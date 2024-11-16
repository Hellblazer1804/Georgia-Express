package com.dbms.georgia_express.model;

public class VerificationResult {
    private boolean approved;
    private String reason;
    private double recommendedCreditLimit;

    public VerificationResult(boolean approved, String reason, double recommendedCreditLimit) {
        this.approved = approved;
        this.reason = reason;
        this.recommendedCreditLimit = recommendedCreditLimit;
    }

    // Getters and setters
    public boolean isApproved() { return approved; }
    public String getReason() { return reason; }
    public double getRecommendedCreditLimit() { return recommendedCreditLimit; }
}
