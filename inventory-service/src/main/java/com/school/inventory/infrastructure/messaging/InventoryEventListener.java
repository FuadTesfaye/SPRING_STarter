package com.school.inventory.infrastructure.messaging;

import com.school.inventory.application.service.InventoryService;
import com.school.inventory.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryEventListener {

    private final InventoryService inventoryService;

    @RabbitListener(queues = RabbitMQConfig.INVENTORY_QUEUE)
    public void handleOrderCreated(OrderCreatedEventDto event) {
        log.info("[INVENTORY] Received order.created for order: {}", event.getOrderId());
        inventoryService.checkAndReserve(event.getOrderId(), event.getStudentId(), event.getFeeType());
    }
}
