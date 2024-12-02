package com.dbms.georgia_express.controller;

import com.dbms.georgia_express.dto.PaymentDTO;
import com.dbms.georgia_express.model.Card;
import com.dbms.georgia_express.dto.CardDTO;
import com.dbms.georgia_express.service.CardService;
import com.dbms.georgia_express.service.CustomerLoginService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private CustomerLoginService customerLoginService;

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    @PostMapping("/verify-credit-card")
    @Operation(summary = "Verifies whether the customer is eligible for a credit card")
    public ResponseEntity<CardDTO> verifyCreditCardEligibility(@RequestHeader("Authorization") String token) {
        CardDTO result = cardService.processCreditCardApplication(token);
        logger.info("Application Processed successfully");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/generate")
    @Operation(summary = "Generates a credit card based on customerId")
    public ResponseEntity<CardDTO> generateCreditCard(@RequestHeader("Authorization") String token) {
        CardDTO generatedCard = cardService.generateCreditCard(token);
        logger.info("Card generated successfully");
        return new ResponseEntity<>(generatedCard, HttpStatus.CREATED);
    }

    @GetMapping("/{cardNumber}")
    @Operation(summary = "Gets information for a credit card")
    public ResponseEntity<?> getCard(@RequestHeader("Authorization") String token, @PathVariable Long cardNumber) {
        if(customerLoginService.getCustomerLoginFromToken(token) == null) {
            logger.error("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CardDTO card = cardService.findByCardNumber(Long.toString(cardNumber));
        if (card == null) {
            logger.error("Card not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        logger.info("Successfully fetched card information");
        return ResponseEntity.ok(card);
    }

    @PutMapping("/{cardNumber}/balance")
    @Operation(summary = "Update a credit card balance")
    public ResponseEntity<CardDTO> updateCardBalance(
            @RequestHeader("Authorization") String token,
            @PathVariable Long cardNumber,
            @RequestParam java.math.BigDecimal balance) {

        if(customerLoginService.getCustomerLoginFromToken(token) == null) {
            logger.error("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CardDTO updatedCard = cardService.updateCardBalance(cardNumber, balance);
        logger.info("Card balance updated successfully");
        return ResponseEntity.ok(updatedCard);
    }

    @PutMapping("/{cardNumber}/minimum-payment")
    @Operation(summary = "Update minimum payment")
    public ResponseEntity<CardDTO> updateMinimumPayment(
            @RequestHeader("Authorization") String token,
            @PathVariable Long cardNumber,
            @RequestParam("amount") BigDecimal minimumPayment) {// specify parameter name
        if(customerLoginService.getCustomerLoginFromToken(token) == null) {
            logger.error("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CardDTO updatedCard = cardService.updateMinimumPayment(cardNumber, minimumPayment);
        return ResponseEntity.ok(updatedCard);
    }

    @PutMapping("/{cardId}/reward_points")
    @Operation(summary = "Add reward points")
    public ResponseEntity<CardDTO> addRewardPoints(
            @RequestHeader("Authorization") String token,
            @PathVariable Long cardId,
            @RequestParam int points) {
        if(customerLoginService.getCustomerLoginFromToken(token) == null) {
            logger.error("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CardDTO updatedCard = cardService.addRewardPoints(cardId, points);
        return ResponseEntity.ok(updatedCard);
    }

    @DeleteMapping("/{cardNumber}")
    @Operation(summary = "Deletes a credit card")
    public ResponseEntity<Void> deleteCard(@RequestHeader("Authorization") String token, @PathVariable String cardNumber) {
        // Optional: Validate card number format
        if(customerLoginService.getCustomerLoginFromToken(token) == null) {
            logger.error("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!isValidCardNumber(cardNumber)) {
            logger.error("Invalid card number format");
            throw new IllegalArgumentException("Invalid card number format");
        }
        cardService.deleteCard(cardNumber);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cardNumber}/payment")
    @Operation(summary = "End point to make a credit card payment")
    public ResponseEntity<PaymentDTO> makePayment(@RequestHeader("Authorization") String token, @PathVariable String cardNumber,
                                                   @RequestParam BigDecimal paymentAmount) {
        if(customerLoginService.getCustomerLoginFromToken(token) == null) {
            logger.error("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        PaymentDTO paymentResult = cardService.makePayment(cardNumber, paymentAmount);
        logger.info("Payment processed successfully");
        return ResponseEntity.ok(paymentResult);
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && cardNumber.matches("\\d{16}"); // Validates 16-digit card number
    }

    @GetMapping("/customer")
    @Operation(summary = "Get the cards' information of a customer")
    public ResponseEntity<List<CardDTO>> getCardByCustomerId(@RequestHeader("Authorization") String token) {
        if(customerLoginService.getCustomerLoginFromToken(token) == null) {
            logger.error("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Integer customerId = customerLoginService.getCustomerLoginFromToken(token).getCustomer().getCustomerId();
        List<CardDTO> card = cardService.findDTOByCustomerId(customerId);
        if (card == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(card);
    }
}
