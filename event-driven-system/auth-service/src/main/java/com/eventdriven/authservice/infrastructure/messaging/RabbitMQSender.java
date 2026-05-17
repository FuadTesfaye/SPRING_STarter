package com.eventdriven.authservice.infrastructure.messaging;

import com.eventdriven.authservice.application.dto.UserRegisteredEvent;
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
    
    public void sendUserRegisteredEvent(UserRegisteredEvent event) {
        log.info("Sending UserRegistered event: {}", event);
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
        log.info("UserRegistered event sent successfully");
    }
}