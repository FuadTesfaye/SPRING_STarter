package com.example.inventory.infrastructure.messaging;

import com.example.inventory.domain.port.InventoryEventPublisherPort;
import com.example.inventory.infrastructure.configuration.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventPublisher implements InventoryEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public void publishReserved(String orderId, String productId, int quantity) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                "stock.reserved",
                new StockReservedEvent(orderId, productId, quantity)
        );
    }

    public void publishFailed(String orderId, String reason) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                "stock.failed",
                new StockFailedEvent(orderId, reason)
        );
    }
}
