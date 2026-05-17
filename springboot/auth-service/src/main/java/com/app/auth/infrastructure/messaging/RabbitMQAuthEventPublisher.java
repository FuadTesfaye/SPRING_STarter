package com.app.auth.infrastructure.messaging;

import com.app.auth.application.ports.AuthEventPublisher;
import com.app.auth.domain.User;
import com.app.auth.domain.UserRegistered;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQAuthEventPublisher implements AuthEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQAuthEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishUserRegistered(User user) {
        UserRegistered event = new UserRegistered(user.getId().toString(), user.getEmail());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.USER_REGISTERED_ROUTING_KEY, event);
    }
}
