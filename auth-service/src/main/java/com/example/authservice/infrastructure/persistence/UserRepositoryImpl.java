package com.example.authservice.infrastructure.persistence;

import com.example.authservice.domain.model.User;
import com.example.authservice.domain.repository.UserRepository;
import com.example.authservice.infrastructure.persistence.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {

        UserEntity entity = new UserEntity();

        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());

        UserEntity saved = jpaUserRepository.save(entity);

        User domainUser = new User();

        domainUser.setId(saved.getId());
        domainUser.setUsername(saved.getUsername());
        domainUser.setEmail(saved.getEmail());
        domainUser.setPassword(saved.getPassword());

        return domainUser;
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return jpaUserRepository.findByEmail(email)
                .map(entity -> {
                    User user = new User();
                    user.setId(entity.getId());
                    user.setUsername(entity.getUsername());
                    user.setEmail(entity.getEmail());
                    user.setPassword(entity.getPassword());
                    return user;
                });
    }
}