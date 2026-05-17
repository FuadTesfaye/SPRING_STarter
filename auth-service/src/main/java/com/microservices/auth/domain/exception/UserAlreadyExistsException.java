package com.microservices.auth.domain.exception;

/**
 * Thrown when trying to register with an email or username that already exists.
 * 
 * WHY SPECIFIC EXCEPTION:
 * - Clear business meaning
 * - Carries information about WHICH field conflicted
 * - Can be mapped to HTTP 409 Conflict in presentation layer
 */
public class UserAlreadyExistsException extends DomainException {
    
    private final String field;  // "email" or "username"
    private final String value;  // The conflicting value
    
    public UserAlreadyExistsException(String field, String value) {
        super(String.format("User already exists with %s: %s", field, value));
        this.field = field;
        this.value = value;
    }
    
    public String getField() {
        return field;
    }
    
    public String getValue() {
        return value;
    }
}
