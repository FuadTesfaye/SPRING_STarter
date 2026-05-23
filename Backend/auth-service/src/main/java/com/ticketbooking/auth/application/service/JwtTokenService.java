package com.ticketbooking.auth.application.service;

public interface JwtTokenService {
    String generateToken(String userId, String email, String role);
    boolean validateToken(String token);
    String extractUserId(String token);
    String extractEmail(String token);
    String extractRole(String token);
}
