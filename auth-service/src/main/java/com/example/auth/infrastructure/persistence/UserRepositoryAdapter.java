package com.microservices.authservice.infrastructure.persistence;

import com.microservices.authservice.domain.model.User;
import com.microservices.authservice.domain.port.UserRepositoryPort;
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