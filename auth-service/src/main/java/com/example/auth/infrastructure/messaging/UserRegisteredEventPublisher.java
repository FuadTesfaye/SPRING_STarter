package com.example.auth.infrastructure.messaging;

import com.example.auth.application.port.out.UserEventPublisher;
import com.example.auth.domain.model.User;
import com.example.events.EventExchange;
import com.example.events.EventRoutingKeys;
import com.example.events.UserRegisteredEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class UserRegisteredEventPublisher implements UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public UserRegisteredEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishUserRegistered(User user) {
        try {
            UserRegisteredEvent event = new UserRegisteredEvent(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    Instant.now()
            );

            rabbitTemplate.convertAndSend(EventExchange.APP_EXCHANGE, EventRoutingKeys.USER_REGISTERED, event);
            System.out.println("📧 NOTIFICATION: User Registered - " + user.getUsername());
        } catch (Exception e) {
            System.err.println("⚠️ Failed to publish UserRegisteredEvent: " + e.getMessage());
            // Don't rethrow - allow registration to succeed even if RabbitMQ is down
        }
    }
}
