package com.example.auth.infrastructure.persistence;

import com.example.auth.domain.model.User;
import com.example.auth.domain.port.UserRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository repository;

    public UserRepositoryAdapter(UserJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        repository.save(entity);
        return user;
    }
}
