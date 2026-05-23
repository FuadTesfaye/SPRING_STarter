package com.example.authservice.application.service;

import com.example.authservice.application.port.*;
import com.example.authservice.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {
    private final UserRepositoryPort users;
    private final PasswordHasherPort hasher;
    private final TokenServicePort tokens;

    public LoginUseCase(UserRepositoryPort users, PasswordHasherPort hasher, TokenServicePort tokens) {
        this.users = users; this.hasher = hasher; this.tokens = tokens;
    }

    public String login(String email, String rawPassword) {
        User u = users.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!hasher.matches(rawPassword, u.getPasswordHash())) throw new IllegalArgumentException("Invalid credentials");
        return tokens.issueToken(u.getId(), u.getEmail());
    }
}
