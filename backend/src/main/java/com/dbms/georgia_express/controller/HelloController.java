package com.dbms.georgia_express.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    @Operation(summary = "Test endpoint to make sure the service is working")
    public String hello() {
        return "Hello World \n" ;
    }

}
