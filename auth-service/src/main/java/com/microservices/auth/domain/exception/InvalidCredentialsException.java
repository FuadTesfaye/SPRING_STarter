package com.microservices.auth.domain.exception;

/**
 * Thrown when login credentials are incorrect.
 * 
 * SECURITY NOTE: We use a generic message intentionally.
 * Don't reveal if email doesn't exist vs password wrong.
 * This prevents user enumeration attacks.
 */
public class InvalidCredentialsException extends DomainException {
    
    public InvalidCredentialsException() {
        super("Invalid email or password");
    }
}
