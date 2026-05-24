package com.ecom.order.application.port;

import com.ecom.order.application.dto.OrderCreatedEvent;

public interface EventPublisher {
    void publishOrderCreated(OrderCreatedEvent event);
}
