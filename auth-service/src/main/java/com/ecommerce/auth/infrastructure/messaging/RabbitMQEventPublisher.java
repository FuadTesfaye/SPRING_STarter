package com.ecommerce.auth.infrastructure.messaging;

import com.ecommerce.auth.application.port.EventPublisher;
import com.ecommerce.auth.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Infrastructure adapter: implements EventPublisher using RabbitMQ.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQEventPublisher implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(String routingKey, Object event) {
        log.info("[AUTH] Publishing event -> routingKey={}, payload={}", routingKey, event);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, routingKey, event);
    }
}
