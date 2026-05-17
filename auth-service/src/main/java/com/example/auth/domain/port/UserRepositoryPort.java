package com.example.auth.domain.port;

import com.example.auth.domain.model.User;

public interface UserRepositoryPort {
    User save(User user);
}
