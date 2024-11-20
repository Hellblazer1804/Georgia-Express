package com.dbms.georgia_express.controller;

import com.dbms.georgia_express.dto.PaymentDTO;
import com.dbms.georgia_express.model.Card;
import com.dbms.georgia_express.dto.CardDTO;
import com.dbms.georgia_express.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/card")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping("/{customerId}/verify-credit-card")
    public ResponseEntity<CardDTO> verifyCreditCardEligibility(@PathVariable Long customerId) {
        CardDTO result = cardService.processCreditCardApplication(customerId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{customerId}/generate")
    public ResponseEntity<CardDTO> generateCreditCard(@PathVariable Long customerId) {
        CardDTO generatedCard = cardService.generateCreditCard(customerId);
        return new ResponseEntity<>(generatedCard, HttpStatus.CREATED);
    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<CardDTO> getCard(@PathVariable Long cardNumber) {
        CardDTO card = cardService.findByCardNumber(Long.toString(cardNumber));
        if (card == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(card);
    }

    @PutMapping("/{cardNumber}/balance")
    public ResponseEntity<CardDTO> updateCardBalance(
            @PathVariable Long cardNumber,
            @RequestParam java.math.BigDecimal balance) {
        CardDTO updatedCard = cardService.updateCardBalance(cardNumber, balance);
        return ResponseEntity.ok(updatedCard);
    }

    @PutMapping("/{cardNumber}/minimum-payment")
    public ResponseEntity<CardDTO> updateMinimumPayment(
            @PathVariable Long cardNumber,
            @RequestParam("amount") BigDecimal minimumPayment) {  // specify parameter name
        CardDTO updatedCard = cardService.updateMinimumPayment(cardNumber, minimumPayment);
        return ResponseEntity.ok(updatedCard);
    }

    @PutMapping("/{cardId}/reward_points")
    public ResponseEntity<CardDTO> addRewardPoints(
            @PathVariable Long cardId,
            @RequestParam int points) {
        CardDTO updatedCard = cardService.addRewardPoints(cardId, points);
        return ResponseEntity.ok(updatedCard);
    }

    @DeleteMapping("/{cardNumber}")
    public ResponseEntity<Void> deleteCard(@PathVariable String cardNumber) {
        // Optional: Validate card number format
        if (!isValidCardNumber(cardNumber)) {
            throw new IllegalArgumentException("Invalid card number format");
        }
        cardService.deleteCard(cardNumber);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cardNumber}/payment")
    public ResponseEntity<PaymentDTO> makePayment( @PathVariable String cardNumber,
                                                   @RequestParam BigDecimal paymentAmount) {
        PaymentDTO paymentResult = cardService.makePayment(cardNumber, paymentAmount);
        return ResponseEntity.ok(paymentResult);
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && cardNumber.matches("\\d{16}"); // Validates 16-digit card number
    }

}
