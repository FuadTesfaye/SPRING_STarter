package com.example.auth.application.dto;

public record UserResponse(
        Long id,
        String username,
        String email
) {
}
