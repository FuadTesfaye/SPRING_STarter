package com.ticketbooking.auth.infrastructure.security;

import com.ticketbooking.auth.application.service.JwtTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private final SecretKey key;
    private final long expirationMs;

    public JwtTokenServiceImpl(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.expiration:86400000}") long expirationMs
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    @Override
    public String generateToken(String userId, String email, String role) {
        return Jwts.builder()
            .subject(userId)
            .claim("email", email)
            .claim("role", role)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expirationMs))
            .signWith(key)
            .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    @Override
    public String extractUserId(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public String extractEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    @Override
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}
