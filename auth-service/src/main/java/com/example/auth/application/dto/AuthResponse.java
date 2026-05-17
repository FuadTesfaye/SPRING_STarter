// auth-service/src/main/java/com/example/auth/application/dto/AuthResponse.java
package com.example.auth.application.dto;

import java.util.UUID;

public class AuthResponse {
    private UUID userId;
    private String username;
    private String email;
    private String token;

    public AuthResponse(UUID userId, String username, String email, String token) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.token = token;
    }

    // Getters
    public UUID getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
}