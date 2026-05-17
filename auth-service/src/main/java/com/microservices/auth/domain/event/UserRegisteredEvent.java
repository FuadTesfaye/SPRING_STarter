package com.microservices.auth.domain.event;

import java.time.LocalDateTime;

/**
 * DOMAIN EVENT: UserRegisteredEvent
 * 
 * WHAT: Something important happened - a user registered!
 * WHY: Decouple services - other services can react to this
 * HOW: Published to RabbitMQ, other services listen
 * 
 * Contains all data needed by listeners
 */
public class UserRegisteredEvent {
    
    private final String userId;
    private final String email;
    private final String username;
    private final LocalDateTime registeredAt;
    
    public UserRegisteredEvent(String userId, String email, String username) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.registeredAt = LocalDateTime.now();
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }
}
