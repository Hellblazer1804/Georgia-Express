package com.dbms.georgia_express.service;

import com.dbms.georgia_express.dto.PaymentDTO;
import com.dbms.georgia_express.model.Customer;
import com.dbms.georgia_express.model.Card;
import com.dbms.georgia_express.dto.CardDTO;
import com.dbms.georgia_express.repositories.CustomerRepository;
import com.dbms.georgia_express.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;


@Service
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
                verificationResult.getRecommendedCreditLimit(),
                "Active",
                true,
                "Approved",
                verificationResult.getRecommendedCreditLimit(),
                customer,
                BigDecimal.ZERO,// Initial minimum payment
                BigDecimal.valueOf(100), // Initial balance
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

    public CardDTO findByCardNumber(String cardId) {
        Card card = cardRepository.findByCardNumber(cardId);
        return mapToCardDTO(card);
    }

    public void deleteCard(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        cardRepository.delete(card);
    }

    public PaymentDTO makePayment(String cardNumber, BigDecimal paymentAmount) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (card == null) {
            throw new RuntimeException("Card not found");
        }

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setCardNumber(cardNumber);

        if (paymentAmount != null) {
            BigDecimal currentBalance = card.getCardBalance();
            BigDecimal newBalance = currentBalance.subtract(paymentAmount);
            card.setCardBalance(newBalance);

            // Update minimum payment if necessary
            if (card.getMinimumPayment().compareTo(newBalance) > 0) {
                card.setMinimumPayment(newBalance);
            }

            // Add reward points (assuming 1 point per every 10 dollars spent)
            int rewardPoints = paymentAmount.divide(BigDecimal.valueOf(10), RoundingMode.DOWN).intValue();
            card.setRewardPoints(card.getRewardPoints() + rewardPoints);

            cardRepository.save(card);

            paymentDTO.setPaymentAmount(paymentAmount);
            paymentDTO.setPaymentStatus("Processed");
            paymentDTO.setVerificationReason("Payment processed successfully");
            return paymentDTO;
        } else {
            // If no payment amount is provided, simply return the current state of the payment
            paymentDTO.setVerificationReason("No payment amount provided");
            paymentDTO.setPaymentStatus("Failed");
            return paymentDTO;
        }
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
        cardDTO.setCardStatus(card.getCardStatus());
        return cardDTO;
    }

    private CardDTO mapToDTOFromVerification(Card verificationResult) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCreditLimit(verificationResult.getRecommendedCreditLimit());
        cardDTO.setApproved(verificationResult.isApproved());
//        cardDTO.setVerificationReason(verificationResult.getReason());

        return cardDTO;
    }
}
