package com.microservices.auth.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * VALUE OBJECT: Email
 * 
 * WHAT: Email address guaranteed to be valid
 * WHY: Validate once, use everywhere. No repeated validation.
 * HOW: Validates format at creation, normalizes to lowercase
 */
public final class Email {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    private final String value;
    
    private Email(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + value);
        }
        // Normalize: lowercase and trim
        this.value = value.toLowerCase().trim();
    }
    
    public static Email of(String email) {
        return new Email(email);
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return value.equals(email.value);
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
