package com.example.order.infrastructure.messaging;

import com.example.order.domain.event.EventPublisher;
import com.example.order.domain.event.OrderCreatedEvent;
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
    public void publishOrderCreated(OrderCreatedEvent event) {
        try {
            log.info("Publishing order created event for order: {}", event.getOrderId());
            rabbitTemplate.convertAndSend("app.exchange", "order.created", event);
            log.info("Event published successfully");
        } catch (Exception e) {
            log.error("Failed to publish event: {}", e.getMessage());
        }
    }
}