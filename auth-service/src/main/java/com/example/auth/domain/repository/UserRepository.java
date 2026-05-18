package com.example.auth.domain.repository;
import com.example.auth.domain.model.User;


import java.util.Optional;


public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
}
