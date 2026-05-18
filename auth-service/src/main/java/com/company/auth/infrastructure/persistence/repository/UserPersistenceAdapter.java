package com.company.auth.infrastructure.persistence.repository;

import com.company.auth.domain.model.User;
import com.company.auth.domain.repository.IUserRepository;
import com.company.auth.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements IUserRepository {

    private final SpringDataUserRepository repository;

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        
        UserEntity saved = repository.save(entity);
        return new User(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getPassword());
    }

    @Override
    public List<User> findAll() {
        return repository.findAll().stream()
                .map(e -> new User(e.getId(), e.getUsername(), e.getEmail(), e.getPassword()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        // Simple implementation for compilation
        return Optional.empty(); 
    }
}
