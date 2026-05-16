package com.example.auth.application.dto;

public record AuthResponse(
        Long userId,
        String username,
        String token
) {
}
