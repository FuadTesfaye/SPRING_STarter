package com.microservices.auth.infrastructure.persistence.mapper;

import com.microservices.auth.domain.model.Email;
import com.microservices.auth.domain.model.Password;
import com.microservices.auth.domain.model.User;
import com.microservices.auth.domain.model.UserId;
import com.microservices.auth.domain.model.Username;
import com.microservices.auth.infrastructure.persistence.entity.UserJpaEntity;

/**
 * PERSISTENCE MAPPER: UserPersistenceMapper
 * 
 * WHAT: Converts between Domain User and JPA Entity
 * WHY: 
 * - Domain objects can't have JPA annotations
 * - JPA entities need specific structure
 * - Clean separation of concerns
 * 
 * CONVERSION FLOW:
 * Domain → JPA: When saving to database
 * JPA → Domain: When loading from database
 */
public final class UserPersistenceMapper {
    
    private UserPersistenceMapper() {
        // Utility class
    }
    
    /**
     * Convert Domain User → JPA Entity (for saving)
     */
    public static UserJpaEntity toJpaEntity(User user) {
        if (user == null) {
            return null;
        }
        
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId().getValue());
        entity.setEmail(user.getEmail().getValue());
        entity.setUsername(user.getUsername().getValue());
        entity.setPassword(user.getPassword().getHashedValue());
        entity.setActive(user.isActive());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());
        entity.setLastLoginAt(user.getLastLoginAt());
        
        return entity;
    }
    
    /**
     * Convert JPA Entity → Domain User (for loading)
     * Uses reconstruct() factory method to rebuild domain object
     */
    public static User toDomain(UserJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return User.reconstruct(
            UserId.fromUUID(entity.getId()),
            Email.of(entity.getEmail()),
            Username.of(entity.getUsername()),
            Password.fromHash(entity.getPassword()),
            entity.isActive(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}
