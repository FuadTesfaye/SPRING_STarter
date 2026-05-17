package com.microservices.auth.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * VALUE OBJECT: UserId
 * 
 * WHAT: Unique identifier for a User
 * WHY: Type safety - can't accidentally use a String as ID
 * HOW: Wraps UUID, always valid if exists
 */
public final class UserId {
    
    private final UUID value;
    
    private UserId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        this.value = value;
    }
    
    // Factory methods for creating UserId
    
    /**
     * Generate a new random UserId (for new users)
     */
    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }
    
    /**
     * Create UserId from String (for API input)
     */
    public static UserId fromString(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("User ID string cannot be empty");
        }
        return new UserId(UUID.fromString(id));
    }
    
    /**
     * Create UserId from UUID object (for database loading)
     * This was the missing method!
     */
    public static UserId fromUUID(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        return new UserId(id);
    }
    
    public UUID getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return value.equals(userId.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
