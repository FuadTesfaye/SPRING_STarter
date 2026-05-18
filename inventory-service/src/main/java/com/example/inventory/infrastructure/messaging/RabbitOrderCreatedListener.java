package com.example.inventory.infrastructure.messaging;

import com.example.inventory.application.usecases.ProcessInventoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RabbitOrderCreatedListener {
    private final ProcessInventoryUseCase useCase;

    @RabbitListener(queues = "inventory.order.created.queue")
    public void handleOrderCreated(Map<String, Object> message) {
        Long orderId = Long.valueOf(message.get("orderId").toString());
        String productId = message.get("productId").toString();
        Integer quantity = Integer.valueOf(message.get("quantity").toString());

        useCase.reserveStock(orderId, productId, quantity);
    }
}