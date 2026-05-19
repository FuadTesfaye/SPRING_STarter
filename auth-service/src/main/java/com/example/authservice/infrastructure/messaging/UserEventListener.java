package com.example.authservice.infrastructure.messaging;

import com.example.authservice.domain.model.UserEntity;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    @RabbitListener(queues = "user.registered.queue")
    public void handleUserRegistered(UserEntity user) {

        System.out.println("📩 Received event:");
        System.out.println("User: " + user.getUsername());
    }
}