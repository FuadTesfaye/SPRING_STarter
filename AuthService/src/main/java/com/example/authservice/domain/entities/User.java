package com.example.authservice.domain.entities;

import com.example.authservice.domain.enums.UserStatus;

public class User {

    private final String id;
    private final String name;
    private final String email;
    private final String password;
    private final UserStatus status;

    public User(String id, String name, String email, String password, UserStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public UserStatus status() {
        return status;
    }

    public boolean passwordMatches(String candidatePassword) {
        return password.equals(candidatePassword);
    }
}
