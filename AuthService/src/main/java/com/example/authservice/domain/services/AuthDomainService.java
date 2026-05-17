package com.example.authservice.domain.services;

import com.example.authservice.domain.entities.User;
import com.example.authservice.domain.enums.UserStatus;
import java.util.UUID;

public class AuthDomainService {

    public User registerUser(String name, String email, String password) {
        return new User(UUID.randomUUID().toString(), name, email, password, UserStatus.REGISTERED);
    }

    public String issueToken() {
        return UUID.randomUUID().toString();
    }
}
