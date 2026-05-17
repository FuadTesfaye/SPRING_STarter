package com.example.auth.infrastructure.messaging;

import com.example.auth.domain.event.EventPublisher;
import com.example.auth.domain.event.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEventPublisher implements EventPublisher {
    
    private static final Logger log = LoggerFactory.getLogger(RabbitMQEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishUserRegistered(UserRegisteredEvent event) {
        try {
            log.info("Publishing user registered event for user: {}", event.getUsername());
            rabbitTemplate.convertAndSend("app.exchange", "user.registered", event);
            log.info("Event published successfully");
        } catch (Exception e) {
            log.error("Failed to publish event: {}", e.getMessage());
        }
    }
}