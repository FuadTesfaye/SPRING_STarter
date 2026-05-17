package com.example.inventservice.infrastructure.messaging;

import com.example.inventservice.application.dto.OrderCreatedEvent;
import com.example.inventservice.application.service.InventoryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final InventoryService inventoryService;

    public OrderCreatedListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("✅ [INVENTORY] Raw message received for processing!");

        try {
            System.out.println("   Order ID : " + event.getOrderId());
            System.out.println("   Product  : " + event.getProductName());
            System.out.println("   Quantity : " + event.getQuantity());
            System.out.println("   Status   : " + event.getStatus());

            inventoryService.checkStock(event);
            System.out.println("✅ [INVENTORY] Message processed successfully!");

        } catch (Exception e) {
            System.err.println("❌ [INVENTORY] CRITICAL ERROR processing message: " + e.getClass().getSimpleName());
            System.err.println("   Message: " + e.getMessage());
            e.printStackTrace();
            throw e; // Let Spring AMQP handle rejection
        }
    }
}