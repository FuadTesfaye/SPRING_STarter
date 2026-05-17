package com.microservices.auth.infrastructure.messaging;

import com.microservices.auth.domain.event.EventPublisher;
import com.microservices.auth.domain.event.UserRegisteredEvent;
import com.microservices.auth.infrastructure.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * RABBITMQ EVENT PUBLISHER
 * 
 * WHAT: Implements domain's EventPublisher using RabbitMQ
 * WHY: 
 * - Domain defines WHAT (publish events)
 * - This implements HOW (using RabbitMQ)
 * - Can swap to Kafka without changing domain code
 * 
 * FLOW:
 * 1. Domain creates event
 * 2. This class sends to RabbitMQ
 * 3. Other services receive and react
 */
@Component
public class RabbitMQEventPublisher implements EventPublisher {
    
    private static final Logger log = LoggerFactory.getLogger(RabbitMQEventPublisher.class);
    
    private final RabbitTemplate rabbitTemplate;
    
    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Override
    public void publish(UserRegisteredEvent event) {
        try {
            log.info("Publishing user registered event for user: {}", event.getUserId());
            
            // Send to RabbitMQ
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,      // Which exchange?
                RabbitMQConfig.USER_REGISTERED_KEY, // Which routing key?
                event                               // The event data
            );
            
            log.info("Event published successfully: {}", event.getUserId());
            
        } catch (Exception e) {
            log.error("Failed to publish event: {}", e.getMessage(), e);
            // In production, you'd have retry logic or DLQ here
        }
    }
}
