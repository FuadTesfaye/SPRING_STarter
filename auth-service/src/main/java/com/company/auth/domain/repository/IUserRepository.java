package com.company.auth.domain.repository;

import com.company.auth.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    User save(User user);
    List<User> findAll();
    Optional<User> findByUsername(String username);
}
