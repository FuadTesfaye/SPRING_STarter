package com.ticketbooking.auth.infrastructure.persistence;

import com.ticketbooking.auth.domain.model.User;
import com.ticketbooking.auth.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpa;

    public UserRepositoryImpl(JpaUserRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public User save(User user) {
        return jpa.save(UserEntity.fromDomain(user)).toDomain();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpa.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpa.findById(id.toString()).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByVerificationToken(String token) {
        return jpa.findByVerificationToken(token).map(UserEntity::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpa.findAll().stream().map(UserEntity::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id.toString());
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }
}
