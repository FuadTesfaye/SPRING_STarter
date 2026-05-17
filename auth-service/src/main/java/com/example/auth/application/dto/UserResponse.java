package com.example.auth.application.dto;

import java.util.UUID;

public class UserResponse {
    private UUID userId;
    private String username;
    private String email;

    public UserResponse(UUID userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public UUID getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}