package com.assignment.assignment.infrastructure.persistence;

import com.assignment.assignment.domain.model.User;
import com.assignment.assignment.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
    
    @Override
    Optional<User> findByEmail(String email);
    
    @Override
    boolean existsByEmail(String email);
}