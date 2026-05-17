package com.microservices.auth.infrastructure.persistence.repository;

import com.microservices.auth.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * SPRING DATA JPA REPOSITORY
 * 
 * WHAT: Spring Data interface for User database operations
 * WHY: 
 * - Spring Data JPA provides automatic implementation
 * - No need to write SQL queries
 * - Method names define queries
 * 
 * HOW IT WORKS:
 * - Extends JpaRepository → gets CRUD methods for free
 * - Method naming convention → automatic query generation
 * - findByEmail → SELECT * FROM users WHERE email = ?
 * - existsByEmail → SELECT COUNT(*) FROM users WHERE email = ?
 */
@Repository
public interface JpaUserRepository extends JpaRepository<UserJpaEntity, UUID> {
    
    /**
     * Find user by email
     * Spring automatically generates: SELECT * FROM users WHERE email = ?
     */
    Optional<UserJpaEntity> findByEmail(String email);
    
    /**
     * Find user by username
     */
    Optional<UserJpaEntity> findByUsername(String username);
    
    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);
    
    /**
     * Check if username exists
     */
    boolean existsByUsername(String username);
}
