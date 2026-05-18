package com.company.order.domain.repository;

import com.company.order.domain.model.Order;

public interface IOrderProducer {
    void sendOrderCreatedEvent(Order order);
}
