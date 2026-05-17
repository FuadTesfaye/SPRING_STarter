package com.microservices.auth.domain.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

/**
 * VALUE OBJECT: Password
 * 
 * WHAT: Secure password handling
 * WHY: Never expose raw password, always hashed
 * HOW: Hashes on creation, only allows comparison
 * 
 * SECURITY: In production, use BCrypt or Argon2 instead of SHA-256
 */
public final class Password {
    
    private static final int MIN_LENGTH = 8;
    
    private final String hashedValue;
    
    private Password(String hashedValue) {
        this.hashedValue = Objects.requireNonNull(hashedValue, "Password hash cannot be null");
    }
    
    /**
     * Create from raw password (registration)
     * Hashes immediately - raw password never stored
     */
    public static Password create(String rawPassword) {
        Objects.requireNonNull(rawPassword, "Password cannot be null");
        if (rawPassword.length() < MIN_LENGTH) {
            throw new IllegalArgumentException(
                "Password must be at least " + MIN_LENGTH + " characters");
        }
        String hashed = hash(rawPassword);
        return new Password(hashed);
    }
    
    /**
     * Create from already hashed password (loading from DB)
     */
    public static Password fromHash(String hashedPassword) {
        return new Password(hashedPassword);
    }
    
    /**
     * Verify if raw password matches stored hash
     */
    public boolean matches(String rawPassword) {
        if (rawPassword == null) {
            return false;
        }
        String hashed = hash(rawPassword);
        return hashed.equals(this.hashedValue);
    }
    
    /**
     * Hash function using SHA-256
     * TODO: Replace with BCrypt for production
     * BCrypt.hashpw(raw, BCrypt.gensalt(12))
     */
    private static String hash(String raw) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not available", e);
        }
    }
    
    /**
     * Get hashed value for database storage
     * IMPORTANT: No getter for raw password!
     */
    public String getHashedValue() {
        return hashedValue;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return hashedValue.equals(password.hashedValue);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(hashedValue);
    }
}
