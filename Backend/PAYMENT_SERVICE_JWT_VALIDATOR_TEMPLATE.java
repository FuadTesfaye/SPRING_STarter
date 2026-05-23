package com.ecommerce.payment.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

/**
 * Payment Service JWT Token Validator
 * 
 * Validates JWT tokens issued by auth-service.
 * Use with SharedJwtValidationFilter.
 */
@Component
public class PaymentJwtValidator {

    private static final Logger logger = LoggerFactory.getLogger(PaymentJwtValidator.class);

    @Value("${jwt.secret:mySecretKeyForEcommerceAuth2024}")
    private String jwtSecret;

    /**
     * Get signing key from secret
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Validate JWT token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
            logger.debug("Token validation successful");
            return true;
        } catch (JwtException ex) {
            logger.debug("JWT validation failed: {}", ex.getMessage());
            return false;
        } catch (IllegalArgumentException ex) {
            logger.debug("JWT claims string is empty: {}", ex.getMessage());
            return false;
        }
    }

    /**
     * Extract username from JWT token
     */
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        } catch (JwtException ex) {
            logger.error("Error extracting username from token: {}", ex.getMessage());
            return null;
        }
    }

    /**
     * Extract userId from JWT token
     */
    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            Object userIdObj = claims.get("userId");
            if (userIdObj instanceof Number) {
                return ((Number) userIdObj).longValue();
            }
            return null;
        } catch (JwtException ex) {
            logger.error("Error extracting userId from token: {}", ex.getMessage());
            return null;
        }
    }
}
