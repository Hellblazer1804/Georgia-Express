package com.dbms.georgia_express.service;

import com.dbms.georgia_express.dto.LoginRequest;
import com.dbms.georgia_express.dto.RegistrationRequest;
import com.dbms.georgia_express.exception.UnauthorizedException;
import com.dbms.georgia_express.model.Customer;
import com.dbms.georgia_express.model.CustomerLogin;
import com.dbms.georgia_express.repositories.CustomerLoginRepository;
import com.dbms.georgia_express.security.JwtTokenUtil;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String register(RegistrationRequest request) {
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

        customerLoginRepository.save(customerLogin);
        return jwtTokenUtil.generateToken(customerLogin.getUsername());

    }

    public String login(LoginRequest request) {
        CustomerLogin customerLogin = customerLoginRepository.findById(request.getUsername()).orElse(null);
        if (customerLogin != null && passwordEncoder.matches(request.getPassword(), customerLogin.getPassword())) {
            return jwtTokenUtil.generateToken(request.getUsername());
        }
        return null;
    }

    private boolean isValidPassword(String password) {
        // Regex enforces to have passwords of at least length 8 of any symbol
        String regex = "^.{8,}$";
        return password.matches(regex);
    }

    public CustomerLogin getCustomerLoginFromToken(String token) {
        System.out.println("Original Token: " + token); // Log the original token
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove the "Bearer " prefix
        }
        System.out.println("Trimmed Token: " + token); // Log the trimmed token

        if (!jwtTokenUtil.validateToken(token)) {
            throw new UnauthorizedException("Invalid token");
        }
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return customerLoginRepository.findById(username)
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));
    }
}