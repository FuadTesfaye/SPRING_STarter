package com.ticketbooking.auth.domain.repository;

import com.ticketbooking.auth.domain.model.User;
import java.util.Optional;
import java.util.UUID;

import java.util.List;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
    Optional<User> findByVerificationToken(String token);
    List<User> findAll();
    void deleteById(UUID id);
    boolean existsByEmail(String email);
}
