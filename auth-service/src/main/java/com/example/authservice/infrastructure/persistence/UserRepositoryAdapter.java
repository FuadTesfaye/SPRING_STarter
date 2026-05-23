package com.example.authservice.infrastructure.persistence;

import com.example.authservice.application.port.UserRepositoryPort;
import com.example.authservice.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final UserJpaRepository jpa;
    public UserRepositoryAdapter(UserJpaRepository jpa) { this.jpa = jpa; }

    @Override public User save(User u) {
        UserEntity e = new UserEntity();
        e.setId(u.getId()); e.setEmail(u.getEmail());
        e.setPasswordHash(u.getPasswordHash()); e.setCreatedAt(u.getCreatedAt());
        UserEntity saved = jpa.save(e);
        return toDomain(saved);
    }
    @Override public Optional<User> findByEmail(String email) { return jpa.findByEmail(email).map(this::toDomain); }
    @Override public boolean existsByEmail(String email) { return jpa.existsByEmail(email); }

    private User toDomain(UserEntity e) {
        return new User(e.getId(), e.getEmail(), e.getPasswordHash(), e.getCreatedAt());
    }
}
