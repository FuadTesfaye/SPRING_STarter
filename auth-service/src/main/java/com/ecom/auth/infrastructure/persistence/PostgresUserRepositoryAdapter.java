package com.ecom.auth.infrastructure.persistence;

import com.ecom.auth.domain.model.User;
import com.ecom.auth.domain.repository.UserRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class PostgresUserRepositoryAdapter implements UserRepository {
    private final JpaUserRepository jpaRepository;

    public PostgresUserRepositoryAdapter(JpaUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity(user.getId(), user.getUsername(), user.getEmail(), user.getPasswordHash());
        UserEntity saved = jpaRepository.save(entity);
        return new User(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getPasswordHash());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(e -> new User(e.getId(), e.getUsername(), e.getEmail(), e.getPasswordHash()));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(e -> new User(e.getId(), e.getUsername(), e.getEmail(), e.getPasswordHash()));
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
