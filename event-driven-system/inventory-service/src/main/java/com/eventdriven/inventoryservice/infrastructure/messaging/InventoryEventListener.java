package com.eventdriven.inventoryservice.infrastructure.messaging;

import com.eventdriven.inventoryservice.application.dto.OrderCreatedEvent;
import com.eventdriven.inventoryservice.application.service.InventoryApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventListener {
    
    private static final Logger log = LoggerFactory.getLogger(InventoryEventListener.class);
    private final InventoryApplicationService inventoryApplicationService;
    
    public InventoryEventListener(InventoryApplicationService inventoryApplicationService) {
        this.inventoryApplicationService = inventoryApplicationService;
    }
    
    @RabbitListener(queues = "inventory.order.created.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Received OrderCreated event: {}", event);
        inventoryApplicationService.processOrder(event);
    }
}