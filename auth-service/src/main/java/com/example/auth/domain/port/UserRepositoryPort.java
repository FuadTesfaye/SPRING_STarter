package com.microservices.authservice.domain.port;

import com.microservices.authservice.domain.model.User;

public interface UserRepositoryPort {
    User save(User user);
}