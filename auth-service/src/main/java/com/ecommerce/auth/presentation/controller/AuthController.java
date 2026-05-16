package com.ecommerce.auth.presentation.controller;

import com.ecommerce.auth.application.service.AuthService;
import com.ecommerce.auth.domain.model.User;
import com.ecommerce.auth.presentation.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "User authentication endpoints")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers user and publishes user.registered event to RabbitMQ")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
        User user = authService.register(req.getEmail(), req.getUsername(), req.getPassword());
        return ResponseEntity.ok(AuthResponse.builder()
                .message("User registered successfully. Event published: user.registered")
                .userId(user.getId())
                .build());
    }

    @PostMapping("/login")
    @Operation(summary = "Login and receive JWT", description = "Validates credentials and returns a JWT token")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        String token = authService.login(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(AuthResponse.builder()
                .message("Login successful")
                .token(token)
                .build());
    }
}
