package com.example.task.inventory.listener;

import com.example.task.inventory.model.ProductStock;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventListener {

    private final RabbitTemplate rabbitTemplate;

    public InventoryEventListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "notificationQueue")
    public void handleOrderCreatedEvent(String message) {
        if (message.startsWith("ORDER_CREATED:")) {
            String[] parts = message.split(":");
            String orderId = parts[1];

            ProductStock stock = new ProductStock("MOCK-PROD-XYZ", 100);
            boolean success = stock.deductStock(1);

            if (success) {
                System.out.println("[INVENTORY SYSTEM] Reserved stock cleanly for order: " + orderId);
                String updatePayload = "STOCK_RESERVED:" + orderId;
                rabbitTemplate.convertAndSend("notificationQueue", updatePayload);
            }
        }
    }
}
