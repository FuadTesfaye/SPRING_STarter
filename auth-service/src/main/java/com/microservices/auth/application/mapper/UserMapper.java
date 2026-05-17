package com.microservices.auth.application.mapper;

import com.microservices.auth.application.dto.response.UserResponse;
import com.microservices.auth.domain.model.User;

/**
 * MAPPER: UserMapper
 * 
 * WHAT: Converts between Domain User and DTOs
 * WHY: 
 * - Domain objects shouldn't leak to outside
 * - DTOs shouldn't contain business logic
 * - Centralized conversion logic
 * 
 * WHY STATIC METHODS:
 * - Simple conversion, no state needed
 * - Lightweight, easy to test
 * - No Spring dependency needed
 */
public final class UserMapper {
    
    // Private constructor - utility class
    private UserMapper() {
    }
    
    /**
     * Convert Domain User → UserResponse DTO
     * 
     * WHY: External systems should see clean DTO, not domain object
     * SECURITY: Password is NOT mapped (never exposed!)
     */
    public static UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        
        return new UserResponse(
            user.getId().getValue().toString(),  // Convert UserId to String
            user.getEmail().getValue(),           // Convert Email to String
            user.getUsername().getValue(),        // Convert Username to String
            user.isActive(),
            user.getCreatedAt(),
            user.getLastLoginAt()
            // NOTE: Password is NEVER mapped!
        );
    }
}
