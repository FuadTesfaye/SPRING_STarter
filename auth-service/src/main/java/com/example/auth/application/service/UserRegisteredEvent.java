package com.microservices.authservice.application.service;

public class UserRegisteredEvent {

    private String username;

    public UserRegisteredEvent() {
    }

    public UserRegisteredEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}