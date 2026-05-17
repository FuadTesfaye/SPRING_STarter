package com.example.inventory.infrastructure.messaging;

import com.example.inventory.application.service.InventoryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventListener {

    private final InventoryService inventoryService;

    public InventoryEventListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_PROCESSED_QUEUE)
    public void handlePaymentProcessed(String username) {
        System.out.println("Inventory-service received payment.processed for: " + username);
        inventoryService.updateInventory(username);
    }
}
