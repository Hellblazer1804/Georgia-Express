package com.dbms.georgia_express.controller;

import com.dbms.georgia_express.dto.TransactionDTO;
import com.dbms.georgia_express.dto.TransactionRequest;
import com.dbms.georgia_express.dto.TransactionResponse;
import com.dbms.georgia_express.model.Transaction;
import com.dbms.georgia_express.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/process")
    @Operation(summary = "Process a transaction based on the username")
    public ResponseEntity<TransactionResponse> processTransaction(@RequestBody TransactionRequest request) {
        try {
            Transaction transaction = transactionService.processTransaction(request.getUsername(),request.getCardNumber());
            TransactionDTO transactionDTO = convertToDTO(transaction);
            TransactionResponse response = new TransactionResponse("Transaction processed successfully", transactionDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            TransactionResponse response = new TransactionResponse("Transaction failed: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction
        );
    }

    @GetMapping("/history")
    @Operation(summary = "Gets the transaction history for a customer")
    public ResponseEntity<List<TransactionDTO>> getTransactionHistory(@RequestHeader String username) {
        List<Transaction> transactions = transactionService.getTransactionHistory(username);
        List<TransactionDTO> transactionDTOs = transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactionDTOs);
    }
}
