package com.school.auth.application.port;

import com.school.auth.domain.entity.User;
import java.util.Optional;

// Port (interface) - defined in application layer, implemented in infrastructure
public interface UserRepository {
    User save(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
