package com.example.orderservice.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final SecretKey key;
    public JwtAuthFilter(@Value("${app.jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            try {
                Claims c = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(auth.substring(7)).getPayload();
                var token = new UsernamePasswordAuthenticationToken(
                    c.getSubject(), null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(token);
                req.setAttribute("userId", c.getSubject());
            } catch (Exception ignored) {}
        }
        chain.doFilter(req, res);
    }
}
