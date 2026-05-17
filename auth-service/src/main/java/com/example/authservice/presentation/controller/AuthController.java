package com.example.authservice.presentation.controller;

import com.example.authservice.application.dto.AuthResponse;
import com.example.authservice.application.dto.LoginRequest;
import com.example.authservice.application.dto.RegisterRequest;
import com.example.authservice.application.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        return authService.login(request);
    }
}