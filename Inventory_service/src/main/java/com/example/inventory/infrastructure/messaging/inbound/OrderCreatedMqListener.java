package com.example.inventory.infrastructure.messaging.inbound;

import com.example.inventory.application.dto.OrderStockCommand;
import com.example.inventory.application.service.InventoryForOrderApplicationService;
import com.example.inventory.infrastructure.configuration.RabbitMQConfig;
import com.example.inventory.infrastructure.messaging.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedMqListener {

    private final InventoryForOrderApplicationService inventoryForOrder;

    //@RabbitListener(queues = RabbitMQConfig.QUEUE_ORDER_CREATED)
    /*ublic void onOrderCreated(OrderCreatedEvent event) {
        inventoryForOrder.reserveStockForOrder(new OrderStockCommand(
                event.getOrderId(),
                event.getProductId(),
                event.getQuantity(),
                event.getTotalAmount()
        ));
    }*/
}
