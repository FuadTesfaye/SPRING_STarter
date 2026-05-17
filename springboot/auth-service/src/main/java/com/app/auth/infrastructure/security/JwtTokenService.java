package com.app.auth.infrastructure.security;

import com.app.auth.application.ports.TokenService;
import com.app.auth.domain.User;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtTokenService implements TokenService {
    // In a real app, use a proper JWT library like jjwt or auth0-jwt
    // For this assignment, we'll simulate token generation to avoid adding too many dependencies
    @Override
    public String generateToken(User user) {
        return "simulated-jwt-token-for-" + user.getEmail() + "-" + UUID.randomUUID();
    }
}
