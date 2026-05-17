package com.microservices.auth.infrastructure.persistence.repository;

import com.microservices.auth.domain.model.Email;
import com.microservices.auth.domain.model.User;
import com.microservices.auth.domain.model.UserId;
import com.microservices.auth.domain.model.Username;
import com.microservices.auth.domain.repository.UserRepository;
import com.microservices.auth.infrastructure.persistence.entity.UserJpaEntity;
import com.microservices.auth.infrastructure.persistence.mapper.UserPersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * REPOSITORY IMPLEMENTATION: UserRepositoryImpl
 * 
 * WHAT: Implements the domain's UserRepository interface
 * WHY: 
 * - Domain defines WHAT (interface)
 * - Infrastructure implements HOW (JPA)
 * - Adapter pattern: adapts JPA to domain interface
 * 
 * HOW IT WORKS:
 * - Receives domain objects → converts to JPA → uses Spring Data → converts back
 * - Domain code never touches JPA entities!
 */
@Component
public class UserRepositoryImpl implements UserRepository {
    
    private final JpaUserRepository jpaUserRepository;
    
    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }
    
    @Override
    public User save(User user) {
        // Convert domain → JPA entity
        UserJpaEntity entity = UserPersistenceMapper.toJpaEntity(user);
        
        // Save using Spring Data JPA
        UserJpaEntity savedEntity = jpaUserRepository.save(entity);
        
        // Convert back → domain
        return UserPersistenceMapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<User> findById(UserId id) {
        return jpaUserRepository.findById(id.getValue())
            .map(UserPersistenceMapper::toDomain);
    }
    
    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaUserRepository.findByEmail(email.getValue())
            .map(UserPersistenceMapper::toDomain);
    }
    
    @Override
    public Optional<User> findByUsername(Username username) {
        return jpaUserRepository.findByUsername(username.getValue())
            .map(UserPersistenceMapper::toDomain);
    }
    
    @Override
    public boolean existsByEmail(Email email) {
        return jpaUserRepository.existsByEmail(email.getValue());
    }
    
    @Override
    public boolean existsByUsername(Username username) {
        return jpaUserRepository.existsByUsername(username.getValue());
    }
    
    @Override
    public void delete(UserId id) {
        jpaUserRepository.deleteById(id.getValue());
    }
}
