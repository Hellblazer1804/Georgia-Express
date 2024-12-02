package com.dbms.georgia_express.service;


import com.dbms.georgia_express.exception.NotFoundException;
import com.dbms.georgia_express.model.Customer;
import com.dbms.georgia_express.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public List<Customer> getAllCustomers() {
        try {
            logger.info("Fetching all customers");
            return customerRepository.findAll();
        } catch (Exception e) {
            logger.error("Error fetching all customers", e);
            throw e;
        }

    }

    public Optional<Customer> getCustomerById(int id) {
        try {
            logger.info("Fetching customer with id: " + id);
            return customerRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error fetching customer with id: " + id, e);
            throw new RuntimeException(e);
        }

    }

    public Customer createCustomer(Customer customer) {
        try {
            logger.info("Creating customer");
            return customerRepository.save(customer);
        } catch (Exception e) {
            logger.error("Error creating customer", e);
            throw e;
        }
    }

    public Customer updateCustomer(int id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));

        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setAddress(customerDetails.getAddress());
        customer.setPhone(customerDetails.getPhone());
        customer.setDateOfBirth(customerDetails.getDateOfBirth());
        customer.setSalary(customerDetails.getSalary());
        customer.setAddress(customerDetails.getAddress());
        customer.setCreditScore(customerDetails.getCreditScore());
        customer.setSsn(customerDetails.getSsn());

        return customerRepository.save(customer);
    }

    public void updateCreditScore(int id, int creditScore) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));

        customer.setCreditScore(creditScore);
        customerRepository.save(customer);
        logger.info("Credit score updated to "+creditScore);
    }

    public void deleteCustomer(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));

        customerRepository.delete(customer);
        logger.info("Customer successfully deleted");
    }

}