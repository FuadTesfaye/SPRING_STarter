package com.example.authservice.presentation.controllers.response;

public record AuthResponse(String token, String status, String message) {
}
