package com.example.authservice.infrastructure.security;
import com.example.authservice.application.port.PasswordHasherPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordHasher implements PasswordHasherPort {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Override public String hash(String raw) { return encoder.encode(raw); }
    @Override public boolean matches(String raw, String hashed) { return encoder.matches(raw, hashed); }
}
