package com.microservices.auth.presentation.advice;

import com.microservices.auth.domain.exception.DomainException;
import com.microservices.auth.domain.exception.InvalidCredentialsException;
import com.microservices.auth.domain.exception.UserAlreadyExistsException;
import com.microservices.auth.domain.exception.UserNotFoundException;
import com.microservices.auth.presentation.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * GLOBAL EXCEPTION HANDLER
 * 
 * WHAT: Catches all exceptions and maps them to HTTP responses
 * WHY:
 * - Clean error handling (no try-catch in every controller)
 * - Consistent error responses
 * - Maps business exceptions to HTTP status codes
 * 
 * HOW IT WORKS:
 * - @RestControllerAdvice applies to all controllers
 * - @ExceptionHandler catches specific exceptions
 * - Returns ResponseEntity with proper HTTP status
 * 
 * MAPPING:
 * - UserAlreadyExistsException → 409 Conflict
 * - InvalidCredentialsException → 401 Unauthorized
 * - UserNotFoundException → 404 Not Found
 * - Validation errors → 400 Bad Request
 * - Other errors → 500 Internal Server Error
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * Handle UserAlreadyExistsException
     * Maps to HTTP 409 Conflict
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(
            UserAlreadyExistsException ex, 
            HttpServletRequest request) {
        
        log.warn("User already exists: {}", ex.getMessage());
        
        ApiError error = new ApiError(
            HttpStatus.CONFLICT.value(),  // 409
            ex.getMessage(),
            request.getRequestURI()
        );
        
        // Add field-specific error
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getField(), "already taken");
        error.setErrors(errors);
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    
    /**
     * Handle InvalidCredentialsException
     * Maps to HTTP 401 Unauthorized
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentials(
            InvalidCredentialsException ex,
            HttpServletRequest request) {
        
        log.warn("Invalid credentials attempt");
        
        ApiError error = new ApiError(
            HttpStatus.UNAUTHORIZED.value(),  // 401
            ex.getMessage(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
    
    /**
     * Handle UserNotFoundException
     * Maps to HTTP 404 Not Found
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(
            UserNotFoundException ex,
            HttpServletRequest request) {
        
        log.warn("User not found: {}", ex.getMessage());
        
        ApiError error = new ApiError(
            HttpStatus.NOT_FOUND.value(),  // 404
            ex.getMessage(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    /**
     * Handle validation errors (@Valid)
     * Maps to HTTP 400 Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        
        log.warn("Validation error: {}", ex.getMessage());
        
        // Collect all field errors
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        
        ApiError error = new ApiError(
            HttpStatus.BAD_REQUEST.value(),  // 400
            "Validation failed",
            request.getRequestURI()
        );
        error.setErrors(errors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    /**
     * Handle all other domain exceptions
     * Maps to HTTP 400 Bad Request
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiError> handleDomainException(
            DomainException ex,
            HttpServletRequest request) {
        
        log.error("Domain error: {}", ex.getMessage());
        
        ApiError error = new ApiError(
            HttpStatus.BAD_REQUEST.value(),  // 400
            ex.getMessage(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    /**
     * Handle all unexpected exceptions
     * Maps to HTTP 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpectedError(
            Exception ex,
            HttpServletRequest request) {
        
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        
        ApiError error = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),  // 500
            "An unexpected error occurred",
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
