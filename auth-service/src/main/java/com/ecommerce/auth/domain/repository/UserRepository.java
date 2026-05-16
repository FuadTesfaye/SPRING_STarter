package com.ecommerce.auth.domain.repository;

import com.ecommerce.auth.domain.model.User;
import java.util.Optional;

/**
 * Domain repository interface - no framework dependencies.
 * Implemented in the Infrastructure layer.
 */
public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
