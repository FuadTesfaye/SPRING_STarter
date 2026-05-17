package com.example.authservice.infrastructure.messaging;

import com.example.authservice.application.port.EventPublisherPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitEventPublisher implements EventPublisherPort {
    private final RabbitTemplate rabbitTemplate;
    @Value("${app.exchange}") private String exchange;
    public RabbitEventPublisher(RabbitTemplate rabbitTemplate) { this.rabbitTemplate = rabbitTemplate; }
    @Override public void publish(String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
    }
}
