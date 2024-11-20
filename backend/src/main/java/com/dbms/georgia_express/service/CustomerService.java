package com.dbms.georgia_express.service;


import com.dbms.georgia_express.exception.NotFoundException;
import com.dbms.georgia_express.model.Customer;
import com.dbms.georgia_express.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(int id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
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

    public void deleteCustomer(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));

        customerRepository.delete(customer);
    }

}