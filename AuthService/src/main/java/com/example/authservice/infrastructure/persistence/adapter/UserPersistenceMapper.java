package com.example.authservice.infrastructure.persistence.adapter;

import com.example.authservice.domain.entities.User;
import com.example.authservice.domain.enums.UserStatus;
import com.example.authservice.infrastructure.persistence.entity.UserJpaEntity;

public class UserPersistenceMapper {

    public UserJpaEntity toEntity(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.id());
        entity.setName(user.name());
        entity.setEmail(user.email());
        entity.setPassword(user.password());
        entity.setStatus(user.status().name());
        return entity;
    }

    public User toDomain(UserJpaEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                UserStatus.valueOf(entity.getStatus())
        );
    }
}
