package com.example.authservice.domain.interfaces;

import com.example.authservice.domain.entities.User;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    User save(User user);
}
