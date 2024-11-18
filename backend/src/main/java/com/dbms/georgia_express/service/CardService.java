package com.dbms.georgia_express.service;

import com.dbms.georgia_express.model.Customer;
import com.dbms.georgia_express.model.Card;
import com.dbms.georgia_express.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CardService {
    @Autowired
    private CustomerVerificationService verificationService;
    private CustomerRepository customerRepository;

    public Card processCreditCardApplication(Long customerId) {
        Customer customer = customerRepository.findById(Math.toIntExact(customerId))
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return verificationService.verifyCustomerForCreditCard(customer);
    }
}
