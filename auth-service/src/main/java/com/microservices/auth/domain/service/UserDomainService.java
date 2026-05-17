package com.microservices.auth.domain.service;

import com.microservices.auth.domain.event.EventPublisher;
import com.microservices.auth.domain.event.UserRegisteredEvent;
import com.microservices.auth.domain.exception.InvalidCredentialsException;
import com.microservices.auth.domain.exception.UserAlreadyExistsException;
import com.microservices.auth.domain.exception.UserNotFoundException;
import com.microservices.auth.domain.model.Email;
import com.microservices.auth.domain.model.Password;
import com.microservices.auth.domain.model.User;
import com.microservices.auth.domain.model.UserId;
import com.microservices.auth.domain.model.Username;
import com.microservices.auth.domain.repository.UserRepository;

/**
 * DOMAIN SERVICE: UserDomainService
 * 
 * WHAT: Core business logic for user operations
 * WHY: Orchestrates domain objects and enforces business rules
 * HOW: Coordinates User, Repository, and Events
 * 
 * This is PURE business logic - no framework code!
 */
public class UserDomainService {
    
    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;
    
    // Constructor injection - dependencies are explicit
    public UserDomainService(UserRepository userRepository, EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }
    
    /**
     * Register a new user
     * 
     * BUSINESS RULES:
     * 1. Email must be unique
     * 2. Username must be unique
     * 3. Password must be valid (handled by Password value object)
     * 4. Publish event after successful registration
     */
    public User register(String email, String username, String password) {
        // Create value objects (validates input)
        Email emailObj = Email.of(email);
        Username usernameObj = Username.of(username);
        Password passwordObj = Password.create(password);
        
        // Check uniqueness
        if (userRepository.existsByEmail(emailObj)) {
            throw new UserAlreadyExistsException("email", email);
        }
        
        if (userRepository.existsByUsername(usernameObj)) {
            throw new UserAlreadyExistsException("username", username);
        }
        
        // Create user using factory method
        User user = User.register(emailObj, usernameObj, passwordObj);
        
        // Save to database
        User savedUser = userRepository.save(user);
        
        // Publish event
        eventPublisher.publish(new UserRegisteredEvent(
            savedUser.getId().getValue().toString(),
            savedUser.getEmail().getValue(),
            savedUser.getUsername().getValue()
        ));
        
        return savedUser;
    }
    
    /**
     * Authenticate user for login
     * 
     * BUSINESS RULES:
     * 1. User must exist with given email
     * 2. User must be active
     * 3. Password must match
     */
    public User login(String email, String password) {
        Email emailObj = Email.of(email);
        
        // Find user
        User user = userRepository.findByEmail(emailObj)
            .orElseThrow(() -> new InvalidCredentialsException());
        
        // Check if user can login
        if (!user.canLogin()) {
            throw new InvalidCredentialsException();
        }
        
        // Verify password
        if (!user.verifyPassword(password)) {
            throw new InvalidCredentialsException();
        }
        
        // Record login
        user.recordLogin();
        userRepository.save(user);
        
        return user;
    }
    
    /**
     * Find user by ID
     */
    public User findById(String id) {
        UserId userId = UserId.fromString(id);
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(id));
    }
    
    /**
     * Deactivate user account
     */
    public void deactivateUser(String id) {
        UserId userId = UserId.fromString(id);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(id));
        
        user.deactivate();
        userRepository.save(user);
    }
}
