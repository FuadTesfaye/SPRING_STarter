package com.example.inventory.infrastructure.messaging;

import com.example.events.OrderCreatedEvent;
import com.example.events.QueueNames;
import com.example.inventory.application.service.InventoryApplicationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final InventoryApplicationService inventoryApplicationService;

    public OrderCreatedListener(InventoryApplicationService inventoryApplicationService) {
        this.inventoryApplicationService = inventoryApplicationService;
    }

    @RabbitListener(queues = QueueNames.INVENTORY_ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        inventoryApplicationService.reserveStock(event);
    }
}
