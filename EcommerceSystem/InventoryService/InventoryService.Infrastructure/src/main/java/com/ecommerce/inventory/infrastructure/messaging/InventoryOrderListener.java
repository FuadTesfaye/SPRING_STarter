package com.ecommerce.inventory.infrastructure.messaging;

import com.ecommerce.inventory.application.usecase.ReserveStockUseCase;
import com.ecommerce.shared.messaging.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryOrderListener {

    private final ReserveStockUseCase reserveStockUseCase;

    @RabbitListener(queues = "inventory.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("INVENTORY: Checking stock for order {}", event.getOrderId());
        
        // Delegate to Application layer
        reserveStockUseCase.reserve(event.getOrderId());
    }
}
