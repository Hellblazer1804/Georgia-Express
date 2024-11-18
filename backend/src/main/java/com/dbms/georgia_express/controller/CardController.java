package com.dbms.georgia_express.controller;

import com.dbms.georgia_express.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dbms.georgia_express.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping("/{Id}/verify-credit-card")
    public ResponseEntity<Card> verifyCreditCardEligibility(
            @PathVariable Long customerId) {
        Card result = cardService.processCreditCardApplication(customerId);
        return ResponseEntity.ok(result);
    }

}
