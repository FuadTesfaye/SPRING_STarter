package com.ecommerce.auth.application.service;

import com.ecommerce.auth.application.ports.EventPublisher;
import org.springframework.stereotype.Service;

@Service
public class AuthApplicationService {

    private final EventPublisher eventPublisher;

    public AuthApplicationService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public String register(String email, String password, String fullName) {
        System.out.println("[AUTH SERVICE] Registering user: " + fullName);
        eventPublisher.publish("USER_REGISTERED:" + email);
        return "User registered successfully";
    }

    public String login(String email, String password) {
        return "Login token generated";
    }
}
