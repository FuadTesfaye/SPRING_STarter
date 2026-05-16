package com.example.order.application.port.out;

import com.example.order.domain.model.Order;

public interface OrderEventPublisher {

    void publishOrderCreated(Order order);
}
