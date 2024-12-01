// src/main/java/com/dbms/georgia_express/controller/CustomerLoginController.java
package com.dbms.georgia_express.controller;

import com.dbms.georgia_express.dto.LoginRequest;
import com.dbms.georgia_express.dto.LoginResponse;
import com.dbms.georgia_express.dto.RegistrationRequest;
import com.dbms.georgia_express.service.CustomerLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class CustomerLoginController {

    @Autowired
    private CustomerLoginService customerLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest customerLogin) {
        String token = customerLoginService.login(customerLogin);
        if (token != null) {
            return ResponseEntity.ok().body(new LoginResponse(token));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest customerRegistration) {
        String token = customerLoginService.register(customerRegistration);
        return ResponseEntity.ok().body(new LoginResponse(token));
    }
}