package com.example.authservice.infrastructure.security;

import com.example.authservice.application.port.TokenServicePort;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenService implements TokenServicePort {
    private final SecretKey key;
    private final long expirationMs;

    public JwtTokenService(@Value("${app.jwt.secret}") String secret,
                           @Value("${app.jwt.expiration}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        this.expirationMs = expirationMs;
    }

    @Override
    public String issueToken(UUID userId, String email) {
        Date now = new Date();
        return Jwts.builder()
            .subject(userId.toString())
            .claim("email", email)
            .issuedAt(now)
            .expiration(new Date(now.getTime() + expirationMs))
            .signWith(key)
            .compact();
    }
}
