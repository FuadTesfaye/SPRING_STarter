package com.microservices.auth.domain.model;

import java.util.Objects;

/**
 * VALUE OBJECT: Username
 * 
 * WHAT: Username with length validation
 * WHY: Business rules in one place
 * HOW: Min 3 chars, max 50 chars, not empty
 */
public final class Username {
    
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 50;
    
    private final String value;
    
    private Username(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (value.length() < MIN_LENGTH) {
            throw new IllegalArgumentException(
                "Username must be at least " + MIN_LENGTH + " characters");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                "Username must be less than " + MAX_LENGTH + " characters");
        }
        this.value = value.trim();
    }
    
    public static Username of(String username) {
        return new Username(username);
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Username username = (Username) o;
        return value.equals(username.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return value;
    }
}
