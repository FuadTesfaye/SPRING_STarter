package com.example.auth.application.dto;

public record LoginCommand(
        String username,
        String password
) {
}
