package com.ecommerce.auth.application.service;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AuthApplicationService {

    public boolean existsByEmail(String email) {
        return false;
    }

    public String register(String email, String password, String fullName) {
        if (existsByEmail(email)) {
            return "User already exists";
        }
        String userId = UUID.randomUUID().toString();
        System.out.println("[AUTH SYSTEM] Registered user: " + fullName + " with ID: " + userId);
        return "User registered successfully! ID: " + userId;
    }

    public String login(String email, String password) {
        if ("admin@test.com".equals(email) && "password".equals(password)) {
            return "Login successful! Token: mock-jwt-token-for-" + email;
        }
        return "Invalid credentials";
    }
}
