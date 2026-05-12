package com.microservices.authservice.application.service;

import com.microservices.authservice.domain.model.User;
import com.microservices.authservice.domain.port.UserRepositoryPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepositoryPort repository;
    private final RabbitTemplate rabbitTemplate;

    public AuthService(UserRepositoryPort repository,
                       RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public User register(User user) {

        repository.save(user);

        rabbitTemplate.convertAndSend(
                "app.exchange",
                "user.registered",
                new UserRegisteredEvent(user.getUsername())
        );

        return user;
    }
}