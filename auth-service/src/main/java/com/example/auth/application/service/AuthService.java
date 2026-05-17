package com.example.auth.application.service;

import com.example.auth.domain.model.User;
import com.example.auth.domain.port.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final UserRepositoryPort repository;
    private final RestTemplate restTemplate;

    public AuthService(UserRepositoryPort repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public User register(User user) {
        repository.save(user);
        System.out.println("USER REGISTERED: " + user.getUsername());
        try {
            restTemplate.postForObject("http://localhost:8082/orders/create?username=" + user.getUsername(), null, String.class);
        } catch (Exception e) {
            System.out.println("Order-service unavailable, skipping order creation: " + e.getMessage());
        }
        return user;
    }
}