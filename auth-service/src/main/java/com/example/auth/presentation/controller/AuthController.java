package com.microservices.authservice.presentation.controller;

import com.microservices.authservice.application.service.AuthService;
import com.microservices.authservice.domain.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }
}