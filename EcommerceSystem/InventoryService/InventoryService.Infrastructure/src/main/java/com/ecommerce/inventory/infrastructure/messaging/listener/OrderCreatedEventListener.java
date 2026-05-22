package com.ecommerce.inventory.infrastructure.messaging.listener;

import com.ecommerce.inventory.application.service.InventoryApplicationService;
import com.ecommerce.shared.messaging.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedEventListener {

    private final InventoryApplicationService inventoryService;

    @RabbitListener(queues = "inventory.order_created.queue")
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent in Inventory: {}", event);
        inventoryService.reserveStock(event.getOrderId());
    }
}
