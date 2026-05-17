package com.example.notification.domain.model;

public class Notification {

    private String message;

    public Notification() {}

    public Notification(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
}
