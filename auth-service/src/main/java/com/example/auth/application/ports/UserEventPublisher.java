package com.example.auth.application.ports;

import com.example.auth.application.dto.AuthResponse;

public interface UserEventPublisher {
    void publishUserRegistered(Long userId, String email);
//    void publishUserLoggedIn(Long userId, String email);
}
