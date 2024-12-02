package com.dbms.georgia_express.service;

import com.dbms.georgia_express.exception.BadRequestException;
import com.dbms.georgia_express.model.Customer;
import com.dbms.georgia_express.model.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class CustomerVerificationService {

    private static final int MINIMUM_AGE = 18;
    private static final double MINIMUM_CREDIT_SCORE = 500;
    private static final double MAX_CREDIT_SCORE = 850;
    private static final Logger logger = LoggerFactory.getLogger(CustomerVerificationService.class);

    public Card verifyCustomerForCreditCard(Customer customer) {
        // 1. Age Verification
        if (!isCustomerAboveMinimumAge(customer.getDateOfBirth())) {
            logger.error("Customer needs to be at least 18");
            throw new BadRequestException("Customer needs to be at least 18");
        }

        // 2. SSN Verification
        if (!isValidSSN(customer.getSsn())) {
            throw new BadRequestException("Invalid SSN");
        }

        // 3. Credit Score Verification
        if (customer.getCreditScore() < MINIMUM_CREDIT_SCORE) {
            logger.error("Credit score must be at least 500");
            throw new BadRequestException("Credit score must be at least 500");
        }

        // 4. Calculate Credit Limit using customer's salary and credit score
        double creditLimit = calculateCreditLimit(customer.getSalary(), customer.getCreditScore());
        logger.info("User verified successfully");
        return new Card(true, creditLimit);
    }

    private boolean isCustomerAboveMinimumAge(String dateOfBirth) {
        try {
            // Define formatters for both date patterns
            DateTimeFormatter formatterYMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter formatterMDY = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            LocalDate birthDate;

            // Try parsing with yyyy-MM-dd format
            try {
                birthDate = LocalDate.parse(dateOfBirth, formatterYMD);
            } catch (DateTimeParseException e) {
                // If that fails, try MM/dd/yyyy format
                birthDate = LocalDate.parse(dateOfBirth, formatterMDY);
            }

            // Calculate age
            return Period.between(birthDate, LocalDate.now()).getYears() >= MINIMUM_AGE;
        } catch (Exception e) {
            // Log the exception for debugging purposes
            logger.error("Error parsing date: {}", e.getMessage());
            return false;
        }
    }

    private boolean isValidSSN(String ssn) {
        // Remove any hyphens or spaces
        String cleanSSN = ssn.replaceAll("[\\s-]", "");

        // Check if SSN is 9 digits
        if (!cleanSSN.matches("\\d{9}")) {
            logger.error("SSN must be 9 digits");
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
                logger.error("SSN cannot have all zeros in any specific group");
                return false;
            }
        }

        // Check if area number is not 666 and not in 900-999 series
        int areaNumber = Integer.parseInt(groups[0]);
        if (areaNumber == 666 || areaNumber >= 900) {
            logger.error("Invalid area number");
            return false;
        }

        return true;
    }

    private double calculateCreditLimit(int yearlySalary, int creditScore) {
        return (yearlySalary / 12.0) * (creditScore / MAX_CREDIT_SCORE);
    }
}
