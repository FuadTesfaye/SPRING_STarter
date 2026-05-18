package com.example.auth.infrastructure.persistence;

import com.example.auth.domain.model.User;
import com.example.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepository {

    private final SpringDataJpaUserRepository repository;

    @Override
    public User save(User user){
        UserEntity userEntity = repository.save(new UserEntity(null, user.getEmail(), user.getUsername(), user.getPassword()));
        return new User(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(), userEntity.getEmail());
    }

    @Override // Added @Override to ensure it matches the interface
    public Optional<User> findByEmail(String email){ // Fixed typo 'findbyEmail' -> 'findByEmail'
        return repository.findByEmail(email)
                .map(e -> new User(e.getId(), e.getUsername(), e.getPassword(), e.getEmail()));
    }
}