package com.microservices.auth.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * API ERROR RESPONSE
 * 
 * WHAT: Standardized error response for all APIs
 * WHY:
 * - Consistent error format
 * - Clients know what to expect
 * - Includes useful debugging info
 * 
 * EXAMPLE RESPONSE:
 * {
 *   "status": 409,
 *   "message": "User already exists with email: john@email.com",
 *   "timestamp": "2026-05-11T12:30:00",
 *   "path": "/api/auth/register",
 *   "errors": {
 *     "email": "already taken"
 *   }
 * }
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private Map<String, String> errors;
    
    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }
    
    public ApiError(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public Map<String, String> getErrors() {
        return errors;
    }
    
    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
