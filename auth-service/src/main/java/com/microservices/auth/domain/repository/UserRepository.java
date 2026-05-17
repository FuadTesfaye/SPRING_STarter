package com.microservices.auth.domain.repository;

import com.microservices.auth.domain.model.Email;
import com.microservices.auth.domain.model.User;
import com.microservices.auth.domain.model.UserId;
import com.microservices.auth.domain.model.Username;

import java.util.Optional;

/**
 * DOMAIN REPOSITORY INTERFACE
 * 
 * WHAT: Contract for User data access
 * WHY: Domain doesn't know about databases
 * HOW: Infrastructure layer implements this
 * 
 * Only defines WHAT operations we need, not HOW to do them
 */
public interface UserRepository {
    
    /**
     * Save user (create or update)
     */
    User save(User user);
    
    /**
     * Find user by ID
     */
    Optional<User> findById(UserId id);
    
    /**
     * Find user by email (for login)
     */
    Optional<User> findByEmail(Email email);
    
    /**
     * Find user by username
     */
    Optional<User> findByUsername(Username username);
    
    /**
     * Check if email already exists (for registration)
     */
    boolean existsByEmail(Email email);
    
    /**
     * Check if username already exists (for registration)
     */
    boolean existsByUsername(Username username);
    
    /**
     * Delete user by ID
     */
    void delete(UserId id);
}
