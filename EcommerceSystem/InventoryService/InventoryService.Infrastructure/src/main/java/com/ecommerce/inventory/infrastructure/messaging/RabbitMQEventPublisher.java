package com.ecommerce.inventory.infrastructure.messaging;

import com.ecommerce.inventory.application.ports.EventPublisher;
import com.ecommerce.shared.messaging.event.BaseEvent;
import com.ecommerce.shared.messaging.event.StockReservedEvent;
import com.ecommerce.shared.messaging.event.StockFailedEvent;
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
        if (event instanceof StockReservedEvent) return "stock.reserved";
        if (event instanceof StockFailedEvent) return "stock.failed";
        return "default.key";
    }
}
