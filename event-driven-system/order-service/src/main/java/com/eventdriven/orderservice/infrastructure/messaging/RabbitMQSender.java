package com.eventdriven.orderservice.infrastructure.messaging;

import com.eventdriven.orderservice.application.dto.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {
    
    private static final Logger log = LoggerFactory.getLogger(RabbitMQSender.class);
    private final RabbitTemplate rabbitTemplate;
    
    @Value("${app.exchange}")
    private String exchange;
    
    @Value("${app.routing-key}")
    private String routingKey;
    
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Sending OrderCreated event: {}", event);
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
        log.info("OrderCreated event sent successfully");
    }
}