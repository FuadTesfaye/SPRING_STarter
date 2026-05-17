package com.example.task.auth;

public class UserRegisteredEvent {
    private String id;
    private String email;
    private String name;

    public UserRegisteredEvent(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
