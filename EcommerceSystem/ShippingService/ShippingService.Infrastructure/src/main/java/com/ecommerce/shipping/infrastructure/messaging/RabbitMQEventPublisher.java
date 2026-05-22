package com.ecommerce.shipping.infrastructure.messaging;

import com.ecommerce.shared.messaging.event.BaseEvent;
import com.ecommerce.shared.messaging.event.ShipmentCreatedEvent;
import com.ecommerce.shipping.application.ports.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQEventPublisher implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;
    public static final String EXCHANGE = "app.exchange";

    @Override
    public void publish(BaseEvent event) {
        String routingKey = getRoutingKey(event);
        rabbitTemplate.convertAndSend(EXCHANGE, routingKey, event);
    }

    private String getRoutingKey(BaseEvent event) {
        if (event instanceof ShipmentCreatedEvent) return "shipment.created";
        return "default.key";
    }
}
