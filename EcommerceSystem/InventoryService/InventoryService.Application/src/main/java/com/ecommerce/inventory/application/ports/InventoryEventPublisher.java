package com.ecommerce.inventory.application.ports;

import com.ecommerce.shared.messaging.event.BaseEvent;

public interface InventoryEventPublisher {
    void publish(BaseEvent event);
}
