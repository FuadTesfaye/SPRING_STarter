package com.example.auth.application.service;

import com.example.auth.domain.model.User;
import com.example.auth.domain.port.UserRepositoryPort;
import com.example.auth.infrastructure.messaging.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepositoryPort repository;
    private final RabbitTemplate rabbitTemplate;

    public AuthService(UserRepositoryPort repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public User register(User user) {
        repository.save(user);
        System.out.println("USER REGISTERED: " + user.getUsername());
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.USER_REGISTERED_EXCHANGE,
            RabbitMQConfig.USER_REGISTERED_ROUTING_KEY,
            user.getUsername()
        );
        return user;
    }
}
