package com.example.order.domain.port;

import com.example.order.domain.model.Order;

public interface OrderEventPublisherPort {

    void publishOrderCreated(Order order, Long userId);
}
