// auth-service/src/main/java/com/example/auth/domain/repository/UserRepository.java
package com.example.auth.domain.repository;

import com.example.auth.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}