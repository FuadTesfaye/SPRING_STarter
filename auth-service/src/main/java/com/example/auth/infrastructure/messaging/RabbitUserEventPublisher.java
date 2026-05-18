package com.example.auth.infrastructure.messaging;
import com.example.auth.application.ports.UserEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RabbitUserEventPublisher implements UserEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void publishUserRegistered(Long userId, String email) {
        Map<String, Object> message = Map.of("userId", userId, "email", email, "event", "USER_REGISTERED");
        rabbitTemplate.convertAndSend("app.exchange", "user.registered", message);
    }
}