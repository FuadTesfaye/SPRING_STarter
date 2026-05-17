package com.example.authservice.infrastructure.persistence.adapter;

import com.example.authservice.domain.entities.User;
import com.example.authservice.domain.interfaces.UserRepository;
import com.example.authservice.infrastructure.persistence.repository.SpringDataUserRepository;
import java.util.Optional;

public class UserPersistenceAdapter implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    public UserPersistenceAdapter(
            SpringDataUserRepository springDataUserRepository,
            UserPersistenceMapper userPersistenceMapper
    ) {
        this.springDataUserRepository = springDataUserRepository;
        this.userPersistenceMapper = userPersistenceMapper;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataUserRepository.findByEmail(email).map(userPersistenceMapper::toDomain);
    }

    @Override
    public User save(User user) {
        return userPersistenceMapper.toDomain(
                springDataUserRepository.save(userPersistenceMapper.toEntity(user))
        );
    }
}
