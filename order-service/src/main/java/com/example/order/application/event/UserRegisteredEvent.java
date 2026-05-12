package com.microservices.orderservice.application.event;

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