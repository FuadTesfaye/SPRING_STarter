package com.example.auth.application.service;

import com.example.auth.application.dto.AuthResponse;
import com.example.auth.application.dto.LoginCommand;
import com.example.auth.application.dto.RegisterUserCommand;
import com.example.auth.application.dto.UserResponse;
import com.example.auth.application.exception.InvalidCredentialsException;
import com.example.auth.application.exception.UserAlreadyExistsException;
import com.example.auth.application.port.out.PasswordHasher;
import com.example.auth.application.port.out.TokenProvider;
import com.example.auth.application.port.out.UserEventPublisher;
import com.example.auth.application.port.out.UserRepository;
import com.example.auth.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit Tests for AuthApplicationService
 * 
 * Tests authentication logic including:
 * - User registration
 * - User login
 * - JWT token generation
 * - Error handling
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Auth Application Service Tests")
class AuthApplicationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHasher passwordHasher;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private UserEventPublisher userEventPublisher;

    private AuthApplicationService authApplicationService;

    @BeforeEach
    void setUp() {
        authApplicationService = new AuthApplicationService(
                userRepository,
                passwordHasher,
                tokenProvider,
                userEventPublisher
        );
    }

    @Test
    @DisplayName("Should register user successfully")
    void testRegisterUserSuccess() {
        // Arrange
        RegisterUserCommand command = new RegisterUserCommand(
                "john_doe",
                "john@example.com",
                "password123"
        );

        User mockUser = new User(1L, "john_doe", "john@example.com", "$2a$10$...");

        when(userRepository.existsByUsername("john_doe")).thenReturn(false);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(passwordHasher.hash("password123")).thenReturn("$2a$10$...");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Act
        UserResponse response = authApplicationService.register(command);

        // Assert
        assertNotNull(response);
        assertEquals("john_doe", response.username());
        assertEquals("john@example.com", response.email());
        verify(userRepository).save(any(User.class));
        verify(userEventPublisher).publishUserRegistered(mockUser);
    }

    @Test
    @DisplayName("Should fail registration when username already exists")
    void testRegisterUserDuplicateUsername() {
        // Arrange
        RegisterUserCommand command = new RegisterUserCommand(
                "john_doe",
                "john@example.com",
                "password123"
        );

        when(userRepository.existsByUsername("john_doe")).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> {
            authApplicationService.register(command);
        });

        verify(userRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Should fail registration when email already exists")
    void testRegisterUserDuplicateEmail() {
        // Arrange
        RegisterUserCommand command = new RegisterUserCommand(
                "john_doe",
                "john@example.com",
                "password123"
        );

        when(userRepository.existsByUsername("john_doe")).thenReturn(false);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> {
            authApplicationService.register(command);
        });

        verify(userRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Should login user successfully")
    void testLoginUserSuccess() {
        // Arrange
        LoginCommand command = new LoginCommand("john_doe", "password123");

        User mockUser = new User(1L, "john_doe", "john@example.com", "$2a$10$...");

        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(mockUser));
        when(passwordHasher.matches("password123", "$2a$10$...")).thenReturn(true);
        when(tokenProvider.generateToken(mockUser)).thenReturn("jwt-token-123");

        // Act
        AuthResponse response = authApplicationService.login(command);

        // Assert
        assertNotNull(response);
        assertEquals("john_doe", response.username());
        assertEquals("jwt-token-123", response.token());
        verify(tokenProvider).generateToken(mockUser);
    }

    @Test
    @DisplayName("Should fail login when user not found")
    void testLoginUserNotFound() {
        // Arrange
        LoginCommand command = new LoginCommand("nonexistent", "password123");

        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> {
            authApplicationService.login(command);
        });

        verify(tokenProvider, times(0)).generateToken(any());
    }

    @Test
    @DisplayName("Should fail login when password is incorrect")
    void testLoginInvalidPassword() {
        // Arrange
        LoginCommand command = new LoginCommand("john_doe", "wrongpassword");

        User mockUser = new User(1L, "john_doe", "john@example.com", "$2a$10$...");

        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(mockUser));
        when(passwordHasher.matches("wrongpassword", "$2a$10$...")).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> {
            authApplicationService.login(command);
        });

        verify(tokenProvider, times(0)).generateToken(any());
    }

    @Test
    @DisplayName("Should publish event after successful registration")
    void testEventPublishingOnRegistration() {
        // Arrange
        RegisterUserCommand command = new RegisterUserCommand(
                "jane_doe",
                "jane@example.com",
                "password456"
        );

        User mockUser = new User(2L, "jane_doe", "jane@example.com", "$2a$10$...");

        when(userRepository.existsByUsername("jane_doe")).thenReturn(false);
        when(userRepository.existsByEmail("jane@example.com")).thenReturn(false);
        when(passwordHasher.hash("password456")).thenReturn("$2a$10$...");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Act
        authApplicationService.register(command);

        // Assert
        verify(userEventPublisher, times(1)).publishUserRegistered(mockUser);
    }
}
