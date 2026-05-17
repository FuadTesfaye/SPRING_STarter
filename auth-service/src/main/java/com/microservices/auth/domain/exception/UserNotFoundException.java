package com.microservices.auth.domain.exception;

/**
 * Thrown when a user is not found by ID, email, or username.
 * Used when we need to find a specific user and they don't exist.
 */
public class UserNotFoundException extends DomainException {
    
    public UserNotFoundException(String identifier) {
        super(String.format("User not found: %s", identifier));
    }
}
