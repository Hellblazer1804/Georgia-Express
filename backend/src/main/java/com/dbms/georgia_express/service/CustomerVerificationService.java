package com.dbms.georgia_express.service;

import com.dbms.georgia_express.model.Customer;
import com.dbms.georgia_express.model.Card;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class CustomerVerificationService {

    private static final int MINIMUM_AGE = 18;
    private static final double MINIMUM_CREDIT_SCORE = 500;
    private static final double MAX_CREDIT_SCORE = 850;

    public Card verifyCustomerForCreditCard(Customer customer) {
        // 1. Age Verification
        if (!isCustomerAboveMinimumAge(customer.getDateOfBirth())) {
            return new Card(false,
                    "Customer must be at least " + MINIMUM_AGE + " years old", 0);
        }

        // 2. SSN Verification
        if (!isValidSSN(customer.getSsn())) {
            return new Card(false,
                    "Invalid SSN format", 0);
        }

        // 3. Credit Score Verification
        if (customer.getCreditScore() < MINIMUM_CREDIT_SCORE) {
            return new Card(false,
                    "Credit score must be at least " + MINIMUM_CREDIT_SCORE, 0);
        }

        // 4. Calculate Credit Limit using customer's salary and credit score
        double creditLimit = calculateCreditLimit(customer.getSalary(), customer.getCreditScore());

        return new Card(true,
                "Customer approved for credit card", creditLimit);
    }

    private boolean isCustomerAboveMinimumAge(String dateOfBirth) {
        try {
            // Parse the date string to LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(dateOfBirth, formatter);

            // Calculate age
            return Period.between(birthDate, LocalDate.now()).getYears() >= MINIMUM_AGE;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidSSN(String ssn) {
        // Remove any hyphens or spaces
        String cleanSSN = ssn.replaceAll("[\\s-]", "");

        // Check if SSN is 9 digits
        if (!cleanSSN.matches("\\d{9}")) {
            return false;
        }

        // Check if SSN is not all zeros in any specific group
        String[] groups = {
                cleanSSN.substring(0, 3),  // Area
                cleanSSN.substring(3, 5),  // Group
                cleanSSN.substring(5, 9)   // Serial
        };

        for (String group : groups) {
            if (group.matches("0+")) {
                return false;
            }
        }

        // Check if area number is not 666 and not in 900-999 series
        int areaNumber = Integer.parseInt(groups[0]);
        if (areaNumber == 666 || areaNumber >= 900) {
            return false;
        }

        return true;
    }

    private double calculateCreditLimit(int yearlySalary, int creditScore) {
        return (yearlySalary / 12.0) * (creditScore / MAX_CREDIT_SCORE);
    }
}
