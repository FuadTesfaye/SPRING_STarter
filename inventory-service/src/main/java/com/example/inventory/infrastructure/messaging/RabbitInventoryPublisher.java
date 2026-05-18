package com.example.inventory.infrastructure.messaging;

import com.example.inventory.application.ports.InventoryEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RabbitInventoryPublisher implements InventoryEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishStockReserved(Long orderId) {
        rabbitTemplate.convertAndSend("app.exchange", "stock.reserved", Map.of("orderId", orderId));
        System.out.println("Published stock.reserved for Order: " + orderId);
    }

    @Override
    public void publishStockFailed(Long orderId, String reason) {
        rabbitTemplate.convertAndSend("app.exchange", "stock.failed", Map.of("orderId", orderId, "reason", reason));
        System.out.println("Published stock.failed for Order: " + orderId);
    }
}