package com.dbms.georgia_express.service;

import com.dbms.georgia_express.dto.CardDTO;
import com.dbms.georgia_express.exception.BadRequestException;
import com.dbms.georgia_express.exception.NotFoundException;
import com.dbms.georgia_express.model.Card;
import com.dbms.georgia_express.model.CartItem;
import com.dbms.georgia_express.model.CustomerLogin;
import com.dbms.georgia_express.model.Transaction;
import com.dbms.georgia_express.repositories.CustomerLoginRepository;
import com.dbms.georgia_express.repositories.CustomerRepository;
import com.dbms.georgia_express.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerLoginService customerLoginService;

    private final static Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public Transaction processTransaction(String token, String cardNumber, int cvv) {
        CustomerLogin customerLogin = customerLoginService.getCustomerLoginFromToken(token);

        List<CartItem> cartItems = cartService.getCart(token);
        BigDecimal totalAmount = getTotalAmount(cartItems);
        CardDTO card = cardService.findByCardNumber(cardNumber);
        if (customerLogin.getCustomer().getCustomerId() != card.getCustomer().getCustomerId()) {
            logger.error("Customer id mismatch");
            throw new BadRequestException("Customer id mismatch");
        }
        if(card.getCvv() != cvv) {
            logger.error("CVV mismatch");
            throw new BadRequestException("CVV mismatch");
        }
        cardService.updateCardBalance(Long.valueOf(card.getCardNumber()),
                card.getCardBalance().add(totalAmount));
        if (card.getCardBalance().divide(BigDecimal.valueOf(card.getCreditLimit()), MathContext.DECIMAL128)
                .compareTo(BigDecimal.valueOf(0.30)) > 0) {
            logger.error("Balance exceeded a certain rate . Credit score dropped");
            customerService.updateCreditScore(customerLogin.getCustomer().getCustomerId(),
                    customerLogin.getCustomer().getCreditScore() - 20);
        }
        Transaction transaction = new Transaction();
        transaction.setCustomerLogin(customerLogin);
        transaction.setAmount(totalAmount);
        transaction.setTransactionDate(String.valueOf(LocalDateTime.now()));
        Transaction savedTransaction = transactionRepository.save(transaction);
        logger.info("Transaction processed successfully");
        cartService.clearCart(token);
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

    public List<Transaction> getTransactionHistory(String token) {
        CustomerLogin customerLogin = customerLoginService.getCustomerLoginFromToken(token);
        try {
            logger.info("Fetching transaction history for customer: " + customerLogin.getCustomer().getName());
            return transactionRepository.findByCustomerLogin(customerLogin);
        } catch (Exception e) {
            logger.error("Error fetching transaction history", e);
            throw e;
        }
    }
}
