package com.dbms.georgia_express.controller;

import com.dbms.georgia_express.dto.LoginRequest;
import com.dbms.georgia_express.dto.RegistrationRequest;
import com.dbms.georgia_express.model.CustomerLogin;
import com.dbms.georgia_express.service.CustomerLoginService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class CustomerLoginController {

    @Autowired
    private CustomerLoginService customerLoginService;

    @PostMapping("/register")
    @Operation(summary = "Registers an user")
    public ResponseEntity<CustomerLogin> register(@RequestBody RegistrationRequest request) {
        CustomerLogin customerLogin = customerLoginService.register(request);
        return ResponseEntity.ok(customerLogin);
    }

    @PostMapping("/login")
    @Operation(summary = "Login a customer")
    public ResponseEntity<CustomerLogin> login(@RequestBody LoginRequest request) {
        CustomerLogin customerLogin = customerLoginService.login(request);
        return ResponseEntity.ok(customerLogin);
    }
}