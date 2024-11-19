package com.dbms.georgia_express.controller;

import com.dbms.georgia_express.model.Card;
import com.dbms.georgia_express.dto.CardDTO;
import com.dbms.georgia_express.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{cardId}")
    public ResponseEntity<CardDTO> getCard(@PathVariable Long cardId) {
        CardDTO card = cardService.findByCardNumber(cardId);
        if (card == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(card);
    }

    @PutMapping("/{cardId}/balance")
    public ResponseEntity<CardDTO> updateCardBalance(
            @PathVariable Long cardId,
            @RequestParam java.math.BigDecimal balance) {
        CardDTO updatedCard = cardService.updateCardBalance(cardId, balance);
        return ResponseEntity.ok(updatedCard);
    }

    @PutMapping("/{cardId}/minimum-payment")
    public ResponseEntity<CardDTO> updateMinimumPayment(
            @PathVariable Long cardId,
            @RequestParam java.math.BigDecimal minimumPayment) {
        CardDTO updatedCard = cardService.updateMinimumPayment(cardId, minimumPayment);
        return ResponseEntity.ok(updatedCard);
    }

    @PutMapping("/{cardId}/reward-points")
    public ResponseEntity<CardDTO> addRewardPoints(
            @PathVariable Long cardId,
            @RequestParam int points) {
        CardDTO updatedCard = cardService.addRewardPoints(cardId, points);
        return ResponseEntity.ok(updatedCard);
    }

}
