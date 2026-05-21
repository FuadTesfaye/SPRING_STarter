package com.ecommerce.inventory.infrastructure.messaging;

import com.ecommerce.inventory.application.ports.InventoryEventPublisher;
import com.ecommerce.shared.messaging.event.BaseEvent;
import com.ecommerce.shared.messaging.event.StockFailedEvent;
import com.ecommerce.shared.messaging.event.StockReservedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventPublisherAdapter implements InventoryEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE = "app.exchange";

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
