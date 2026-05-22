package com.ecommerce.inventory.application.service;

import com.ecommerce.inventory.application.ports.EventPublisher;
import com.ecommerce.shared.messaging.event.StockReservedEvent;
import com.ecommerce.shared.messaging.event.StockFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryApplicationService {

    private final EventPublisher eventPublisher;

    public void reserveStock(String orderId) {
        // Mock inventory logic: succeed 90% of the time
        boolean success = Math.random() < 0.9;

        if (success) {
            eventPublisher.publish(new StockReservedEvent(orderId));
        } else {
            eventPublisher.publish(new StockFailedEvent(orderId, "Product out of stock"));
        }
    }
}
