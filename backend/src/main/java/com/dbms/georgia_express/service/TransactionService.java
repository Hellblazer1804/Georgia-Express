package com.dbms.georgia_express.service;

import com.dbms.georgia_express.exception.NotFoundException;
import com.dbms.georgia_express.model.Card;
import com.dbms.georgia_express.model.CartItem;
import com.dbms.georgia_express.model.CustomerLogin;
import com.dbms.georgia_express.model.Transaction;
import com.dbms.georgia_express.repositories.CustomerLoginRepository;
import com.dbms.georgia_express.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CustomerLoginRepository customerLoginRepository;

    public Transaction processTransaction(String username) {
        CustomerLogin customerLogin = customerLoginRepository.findById(username)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        List<CartItem> cartItems = cartService.getCart(username);
        BigDecimal totalAmount = getTotalAmount(cartItems);
        Card card = cardService.findByCustomerId(customerLogin.getCustomer().getCustomerId());
        cardService.updateCardBalance(Long.valueOf(card.getCardNumber()),
                card.getCardBalance().add(totalAmount));
        Transaction transaction = new Transaction();
        transaction.setCustomerLogin(customerLogin);
        transaction.setAmount(totalAmount);
        transaction.setTransactionDate(String.valueOf(LocalDateTime.now()));
        Transaction savedTransaction = transactionRepository.save(transaction);
        cartService.clearCart(username);
        return transaction;
    }

    private BigDecimal getTotalAmount(List<CartItem > cartItems) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            totalAmount = totalAmount.add( BigDecimal.valueOf(cartItem.getItem().getCost()).
                    multiply( BigDecimal.valueOf(cartItem.getQuantity())));
        }
        return totalAmount;
    }

    public List<Transaction> getTransactionHistory(String username) {
        CustomerLogin customerLogin = customerLoginRepository.findById(username)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        return transactionRepository.findByCustomerLogin(customerLogin);
    }
}
