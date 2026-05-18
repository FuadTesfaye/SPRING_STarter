package com.company.auth.infrastructure.messaging.rabbitmq;

import com.company.auth.domain.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitUserProducer {
    private final RabbitTemplate rabbitTemplate;
    
    public static final String EXCHANGE = "auth.exchange";
    public static final String ROUTING_KEY = "user.registered";

    @EventListener
    public void handleUserRegistered(UserRegisteredEvent event) {
        System.out.println("Publishing UserRegisteredEvent to RabbitMQ: " + event.getUsername());
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, event);
    }
}
