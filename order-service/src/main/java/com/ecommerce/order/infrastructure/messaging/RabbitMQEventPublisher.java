package com.ecommerce.order.infrastructure.messaging;

import com.ecommerce.order.application.port.EventPublisher;
import com.ecommerce.order.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQEventPublisher implements EventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(String routingKey, Object event) {
        log.info("[ORDER] Publishing event -> routingKey={}", routingKey);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, routingKey, event);
    }
}
