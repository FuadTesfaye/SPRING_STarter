package com.school.auth.infrastructure.messaging;

import com.school.auth.application.port.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQEventPublisher implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Override
    public void publish(String routingKey, Object event) {
        log.info("Publishing event with routing key: {}", routingKey);
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
        log.info("Event published successfully: {}", routingKey);
    }
}
