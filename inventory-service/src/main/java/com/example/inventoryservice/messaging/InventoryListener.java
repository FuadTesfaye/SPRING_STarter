package com.example.inventoryservice.messaging;

import com.example.inventoryservice.config.RabbitMQConfig;
import com.example.inventoryservice.dto.InventoryEvent;
import com.example.inventoryservice.dto.OrderEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryListener {

    private final RabbitTemplate rabbitTemplate;

    public InventoryListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "order.queue")
    public void checkStock(OrderEvent order) {

        System.out.println("Inventory received order: " + order.getOrderId());

        // MOCK STOCK LOGIC
        boolean inStock = order.getQuantity() <= 10;

        if (inStock) {

            InventoryEvent event =
                    new InventoryEvent(order.getOrderId(), "RESERVED");

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    "stock.reserved",
                    event
            );

            System.out.println("STOCK RESERVED");

        } else {

            InventoryEvent event =
                    new InventoryEvent(order.getOrderId(), "FAILED");

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    "stock.failed",
                    event
            );

            System.out.println("STOCK FAILED");
        }
    }
}