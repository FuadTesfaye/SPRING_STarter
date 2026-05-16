package com.ecommerce.shipping.infrastructure.messaging;

import com.ecommerce.shipping.application.port.EventPublisher;
import com.ecommerce.shipping.infrastructure.config.RabbitMQConfig;
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
        log.info("[SHIPPING] Publishing event -> routingKey={}", routingKey);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, routingKey, event);
    }
}
