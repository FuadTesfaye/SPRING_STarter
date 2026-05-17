package com.microservices.order.infrastructure.messaging;

import com.microservices.order.domain.event.OrderCreatedEvent;
import com.microservices.order.domain.event.OrderEventPublisher;
import com.microservices.order.infrastructure.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * RABBITMQ EVENT PUBLISHER
 * 
 * WHAT: Implements domain's OrderEventPublisher using RabbitMQ
 * WHY: Domain defines WHAT, this implements HOW
 * 
 * Publishes order.created events so Payment & Inventory can react
 */
@Component
public class RabbitMQOrderEventPublisher implements OrderEventPublisher {
    
    private static final Logger log = LoggerFactory.getLogger(RabbitMQOrderEventPublisher.class);
    
    private final RabbitTemplate rabbitTemplate;
    
    public RabbitMQOrderEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Override
    public void publishOrderCreated(OrderCreatedEvent event) {
        try {
            log.info("Publishing order created event: {}", event.getOrderId());
            
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ORDER_CREATED_KEY,
                event
            );
            
            log.info("Order created event published successfully");
            
        } catch (Exception e) {
            log.error("Failed to publish order created event: {}", e.getMessage(), e);
        }
    }
}
