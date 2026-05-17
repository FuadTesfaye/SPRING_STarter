package com.microservices.auth.infrastructure.security;

import com.microservices.auth.infrastructure.config.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * JWT TOKEN PROVIDER
 * 
 * WHAT: Handles JWT token generation and validation
 * WHY: Centralized token logic
 */
@Component
public class JwtTokenProvider {
    
    private final SecretKey secretKey;
    private final long expirationMs;
    
    // Fixed: Added JwtConfig import
    public JwtTokenProvider(JwtConfig jwtConfig) {
        byte[] keyBytes = Base64.getDecoder().decode(jwtConfig.getSecret());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMs = jwtConfig.getExpiration();
    }
    
    /**
     * Generate JWT token for authenticated user
     */
    public String generateToken(String userId, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);
        
        return Jwts.builder()
            .subject(userId)
            .claim("email", email)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey)
            .compact();
    }
    
    /**
     * Extract user ID from token
     */
    public String getUserIdFromToken(String token) {
        return parseToken(token)
            .getPayload()
            .getSubject();
    }
    
    /**
     * Extract email from token
     */
    public String getEmailFromToken(String token) {
        return parseToken(token)
            .getPayload()
            .get("email", String.class);
    }
    
    /**
     * Validate if token is valid and not expired
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Parse and verify token
     */
    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token);
    }
}
