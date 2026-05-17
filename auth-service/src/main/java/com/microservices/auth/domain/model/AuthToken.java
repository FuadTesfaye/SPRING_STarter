package com.microservices.auth.domain.model;

import java.time.LocalDateTime;

/**
 * DOMAIN OBJECT: AuthToken
 * 
 * WHAT: Represents a JWT authentication token
 * WHY: Encapsulates token logic (expiry, validation)
 * HOW: Tracks creation time, expiry, and revocation status
 */
public class AuthToken {
    
    private final String token;
    private final String userId;
    private final LocalDateTime issuedAt;
    private final LocalDateTime expiresAt;
    private boolean revoked;
    
    public AuthToken(String token, String userId, LocalDateTime expiresAt) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token cannot be empty");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        if (expiresAt == null) {
            throw new IllegalArgumentException("Expiry time cannot be null");
        }
        
        this.token = token;
        this.userId = userId;
        this.issuedAt = LocalDateTime.now();
        this.expiresAt = expiresAt;
        this.revoked = false;
    }
    
    /**
     * Check if token is currently valid
     * Valid = not revoked AND not expired
     */
    public boolean isValid() {
        return !revoked && !isExpired();
    }
    
    /**
     * Check if token has expired
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
    
    /**
     * Revoke this token (logout)
     */
    public void revoke() {
        this.revoked = true;
    }
    
    // Getters
    public String getToken() {
        return token;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }
    
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    
    public boolean isRevoked() {
        return revoked;
    }
}
