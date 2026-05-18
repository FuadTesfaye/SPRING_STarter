package com.example.auth.presentation.rest;

import com.example.auth.application.dto.AuthResponse;
import com.example.auth.application.dto.LoginRequest;
import com.example.auth.application.dto.RegisterRequest;
import com.example.auth.application.usecases.AuthUseCase;
import com.example.auth.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth") @RequiredArgsConstructor
public class AuthController {
    private final AuthUseCase authUseCase;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authUseCase.register(request);
        return ResponseEntity.ok("User registered and event published to RabbitMQ");
    }

    @PostMapping("/login") // <--- MAKE SURE THIS EXISTS
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authUseCase.login(request);
        return ResponseEntity.ok("Login successful, token: " + token);
    }
}
