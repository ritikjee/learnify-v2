package com.learnify.auth_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/register")
    public String getMethodName() {
        return new String("Hello World");
    }

}
