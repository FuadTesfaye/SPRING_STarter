package com.ecommerce.order.infrastructure.messaging;

import com.ecommerce.order.application.ports.EventPublisher;
import com.ecommerce.shared.messaging.event.BaseEvent;
import com.ecommerce.shared.messaging.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE = "app.exchange";

    @Override
    public void publish(BaseEvent event) {
        String routingKey = getRoutingKey(event);
        rabbitTemplate.convertAndSend(EXCHANGE, routingKey, event);
    }

    private String getRoutingKey(BaseEvent event) {
        if (event instanceof OrderCreatedEvent) return "order.created";
        return "default.key";
    }
}
