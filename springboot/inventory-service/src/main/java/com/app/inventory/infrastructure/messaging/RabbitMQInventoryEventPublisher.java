package com.app.inventory.infrastructure.messaging;

import com.app.inventory.application.ports.InventoryEventPublisher;
import com.app.inventory.domain.StockFailed;
import com.app.inventory.domain.StockReserved;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class RabbitMQInventoryEventPublisher implements InventoryEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQInventoryEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishStockReserved(UUID orderId) {
        StockReserved event = new StockReserved(orderId);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.STOCK_RESERVED_ROUTING_KEY, event);
    }

    @Override
    public void publishStockFailed(UUID orderId, String reason) {
        StockFailed event = new StockFailed(orderId, reason);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.STOCK_FAILED_ROUTING_KEY, event);
    }
}
