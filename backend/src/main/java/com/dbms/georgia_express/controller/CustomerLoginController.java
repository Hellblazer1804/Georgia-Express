// src/main/java/com/dbms/georgia_express/controller/CustomerLoginController.java
package com.dbms.georgia_express.controller;

import com.dbms.georgia_express.dto.LoginRequest;
import com.dbms.georgia_express.dto.LoginResponse;
import com.dbms.georgia_express.dto.RegistrationRequest;
import com.dbms.georgia_express.service.CustomerLoginService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Logs in an user")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest customerLogin) {
        String token = customerLoginService.login(customerLogin);
        if (token != null) {
            return ResponseEntity.ok().body(new LoginResponse(token));
        }
        return ResponseEntity.status(401).body(new LoginResponse("Invalid credentials"));
    }

    @PostMapping("/register")
    @Operation(summary = "Registers an user")
    public ResponseEntity<LoginResponse> register(@RequestBody RegistrationRequest customerRegistration) {
        String token = customerLoginService.register(customerRegistration);
        return ResponseEntity.ok().body(new LoginResponse(token));
    }
}