package com.dbms.georgia_express.service;

import com.dbms.georgia_express.dto.LoginRequest;
import com.dbms.georgia_express.dto.RegistrationRequest;
import com.dbms.georgia_express.model.Customer;
import com.dbms.georgia_express.model.CustomerLogin;
import com.dbms.georgia_express.repositories.CustomerLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerLoginService {

    @Autowired
    private CustomerLoginRepository customerLoginRepository;

    @Autowired
    private CustomerService customerService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CustomerLogin register(RegistrationRequest request) {
        if (customerLoginRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (!isValidPassword(request.getPassword())) {
            throw new RuntimeException("Password must be 8 characters long and contain any symbols");
        }

        Customer customer = customerService.getCustomerById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerLogin customerLogin = new CustomerLogin();
        customerLogin.setUsername(request.getUsername());
        customerLogin.setPassword(passwordEncoder.encode(request.getPassword()));
        customerLogin.setCustomer(customer);

        return customerLoginRepository.save(customerLogin);
    }

    public CustomerLogin login(LoginRequest request) {
        CustomerLogin customerLogin = customerLoginRepository.findById(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), customerLogin.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return customerLogin;
    }


    private boolean isValidPassword(String password) {
        // Regex enforces to have passwords of at least length 8 of any symbol
        String regex = "^.{8,}$";
        return password.matches(regex);
    }
}