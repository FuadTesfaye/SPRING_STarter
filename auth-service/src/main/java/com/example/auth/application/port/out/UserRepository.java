package com.example.auth.application.port.out;

import com.example.auth.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
}
