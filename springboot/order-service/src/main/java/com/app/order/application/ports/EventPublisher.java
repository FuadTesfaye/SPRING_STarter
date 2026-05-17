package com.app.order.application.ports;

import com.app.order.domain.Order;

public interface EventPublisher {
    void publishOrderCreated(Order order);
}
