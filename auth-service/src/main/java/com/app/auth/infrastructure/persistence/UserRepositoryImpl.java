package com.app.auth.infrastructure.persistence;

import com.app.auth.domain.User;
import com.app.auth.domain.UserRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SpringDataUserRepository springDataUserRepository;

    public UserRepositoryImpl(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity(user.getId(), user.getEmail(), user.getPasswordHash(), user.getFullName());
        UserEntity saved = springDataUserRepository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataUserRepository.findByEmail(email).map(this::mapToDomain);
    }

    private User mapToDomain(UserEntity entity) {
        return new User(entity.getId(), entity.getEmail(), entity.getPasswordHash(), entity.getFullName());
    }
}
