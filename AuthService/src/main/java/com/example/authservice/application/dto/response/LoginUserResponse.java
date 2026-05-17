package com.example.authservice.application.dto.response;

public record LoginUserResponse(String token, String status, String message) {
}
