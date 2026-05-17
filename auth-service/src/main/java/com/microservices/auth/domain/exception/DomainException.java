package com.microservices.auth.domain.exception;

/**
 * Base exception for all domain exceptions.
 * 
 * WHY: Provides a common parent for all business exceptions.
 * This allows catching all domain errors with one catch block.
 */
public class DomainException extends RuntimeException {
    
    public DomainException(String message) {
        super(message);
    }
    
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
