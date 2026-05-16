package com.ecommerce.inventory.infrastructure.messaging;

import com.ecommerce.inventory.application.service.InventoryService;
import com.ecommerce.inventory.domain.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedConsumer {

    private final InventoryService inventoryService;

    @RabbitListener(queues = "inventory.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("[INVENTORY] Received order.created event for order: {}", event.getOrderId());
        inventoryService.checkAndReserve(event);
    }
}
