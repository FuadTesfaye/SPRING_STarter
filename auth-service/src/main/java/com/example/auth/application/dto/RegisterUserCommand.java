package com.example.auth.application.dto;

public record RegisterUserCommand(
        String username,
        String email,
        String password
) {
}
