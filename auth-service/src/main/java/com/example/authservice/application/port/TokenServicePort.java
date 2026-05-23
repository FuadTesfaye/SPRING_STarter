package com.example.authservice.application.port;
import java.util.UUID;
public interface TokenServicePort {
    String issueToken(UUID userId, String email);
}
