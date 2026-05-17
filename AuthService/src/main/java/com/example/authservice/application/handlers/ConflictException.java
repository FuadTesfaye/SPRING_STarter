package com.example.authservice.application.handlers;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
