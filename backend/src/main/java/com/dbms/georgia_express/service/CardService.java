package com.dbms.georgia_express.service;

import com.dbms.georgia_express.model.Customer;
import com.dbms.georgia_express.model.Card;
import com.dbms.georgia_express.dto.CardDTO;
import com.dbms.georgia_express.repositories.CustomerRepository;
import com.dbms.georgia_express.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

//public class CardService {
//    @Autowired
//    private CustomerVerificationService verificationService;
//    private CustomerRepository customerRepository;
//    private CardRepository cardRepository;
//
//    public Card processCreditCardApplication(Long customerId) {
//        Customer customer = customerRepository.findById(Math.toIntExact(customerId))
//                .orElseThrow(() -> new RuntimeException("Customer not found"));
//
//        return verificationService.verifyCustomerForCreditCard(customer);
//    }
//
//    public Card generateCreditCard(Long customerId) {
//        Customer customer = customerRepository.findById(Math.toIntExact(customerId))
//                .orElseThrow(() -> new RuntimeException("Customer not found"));
//
//        Card verificationResult = verificationService.verifyCustomerForCreditCard(customer);
//
//        if (!verificationResult.isApproved()) {
//            throw new RuntimeException("Customer is not eligible for a credit card: " + verificationResult.getReason());
//        }
//
//        String cardNumber = generateCardNumber();
//        String expiryDate = generateExpiryDate();
//        int cvv = generateCVV();
//
//        Card newCard = new Card(
//                cardNumber,
//                expiryDate,
//                cvv,
//                verificationResult.getRecommendedCreditLimit(),
//                "Active",
//                true,
//                "Approved",
//                verificationResult.getRecommendedCreditLimit(),
//                customer,
//                BigDecimal.ZERO, // Initial balance
//                BigDecimal.ZERO, // Initial minimum payment
//                0 // Initial reward pointss
//        );
//
//        return cardRepository.save(newCard);
//    }
//
//    private String generateCardNumber() {
//        Random random = new Random();
//        StringBuilder sb = new StringBuilder(16);
//        for (int i = 0; i < 16; i++) {
//            sb.append(random.nextInt(10));
//        }
//        return sb.toString();
//    }
//
//    private String generateExpiryDate() {
//        LocalDate expiryDate = LocalDate.now().plusYears(5);
//        return expiryDate.format(DateTimeFormatter.ofPattern("MM/yy"));
//    }
//
//    private int generateCVV() {
//        return new Random().nextInt(900) + 100; // Generates a number between 100 and 999
//    }
//
//    // Additional methods for card management
//
//    public Card updateCardBalance(Long cardId, BigDecimal newBalance) {
//        Card card = cardRepository.findById(cardId)
//                .orElseThrow(() -> new RuntimeException("Card not found"));
//        card.setCardBalance(newBalance);
//        return cardRepository.save(card);
//    }
//
//    public Card updateMinimumPayment(Long cardId, BigDecimal minimumPayment) {
//        Card card = cardRepository.findById(cardId)
//                .orElseThrow(() -> new RuntimeException("Card not found"));
//        card.setMinimumPayment(minimumPayment);
//        return cardRepository.save(card);
//    }
//
//    public Card addRewardPoints(Long cardId, int points) {
//        Card card = cardRepository.findById(cardId)
//                .orElseThrow(() -> new RuntimeException("Card not found"));
//        card.setRewardPoints(card.getRewardPoints() + points);
//        return cardRepository.save(card);
//    }
//}

public class CardService {

    @Autowired
    private CustomerVerificationService verificationService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CardRepository cardRepository;

    public CardDTO processCreditCardApplication(Long customerId) {
        Customer customer = customerRepository.findById(Math.toIntExact(customerId))
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Card verificationResult = verificationService.verifyCustomerForCreditCard(customer);
        return mapToDTOFromVerification(verificationResult);
    }

    public CardDTO generateCreditCard(Long customerId) {
        Customer customer = customerRepository.findById(Math.toIntExact(customerId))
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Card verificationResult = verificationService.verifyCustomerForCreditCard(customer);

        if (!verificationResult.isApproved()) {
            throw new RuntimeException("Customer is not eligible for a credit card.");
        }

        String cardNumber = generateCardNumber();
        String expiryDate = generateExpiryDate();
        int cvv = generateCVV();

        Card newCard = new Card(
                cardNumber,
                expiryDate,
                cvv,
                verificationResult.getCreditLimit(),
                "Active",
                true,
                "Approved",
                verificationResult.getCreditLimit(),
                customer,
                BigDecimal.ZERO, // Initial balance
                BigDecimal.ZERO, // Initial minimum payment
                0 // Initial reward points
        );

        Card savedCard = cardRepository.save(newCard);
        return mapToCardDTO(savedCard);
    }

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String generateExpiryDate() {
        LocalDate expiryDate = LocalDate.now().plusYears(5);
        return expiryDate.format(DateTimeFormatter.ofPattern("MM/yy"));
    }

    private int generateCVV() {
        return new Random().nextInt(900) + 100; // Generates a number between 100 and 999
    }

    // Additional methods for card management

    public CardDTO updateCardBalance(Long cardId, BigDecimal newBalance) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        card.setCardBalance(newBalance);
        Card updatedCard = cardRepository.save(card);
        return mapToCardDTO(updatedCard);
    }

    public CardDTO updateMinimumPayment(Long cardId, BigDecimal minimumPayment) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        card.setMinimumPayment(minimumPayment);
        Card updatedCard = cardRepository.save(card);
        return mapToCardDTO(updatedCard);
    }

    public CardDTO addRewardPoints(Long cardId, int points) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        card.setRewardPoints(card.getRewardPoints() + points);
        Card updatedCard = cardRepository.save(card);
        return mapToCardDTO(updatedCard);
    }

    public CardDTO findByCardNumber(Long cardId) {
        Card card = cardRepository.findById(cardId) .orElseThrow(() -> new RuntimeException("Card not found"));
        return mapToCardDTO(card);
    }

    private CardDTO mapToCardDTO(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(card.getCardNumber());
        cardDTO.setExpiryDate(card.getExpiryDate());
        cardDTO.setCvv(card.getCvv());
        cardDTO.setCreditLimit(card.getCreditLimit());
        cardDTO.setApproved(card.isApproved());
        cardDTO.setVerificationReason(card.getVerificationReason());
        cardDTO.setRecommendedCreditLimit(card.getRecommendedCreditLimit());
        cardDTO.setCustomer(card.getCustomer());
        cardDTO.setCardBalance(card.getCardBalance());
        cardDTO.setMinimumPayment(card.getMinimumPayment());
        cardDTO.setRewardPoints(card.getRewardPoints());
        return cardDTO;
    }

    private CardDTO mapToDTOFromVerification(Card verificationResult) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCreditLimit(verificationResult.getRecommendedCreditLimit());
        cardDTO.setApproved(verificationResult.isApproved());
        cardDTO.setVerificationReason(verificationResult.getReason());

        return cardDTO;
    }
}
