package com.example.order.domain.port;

import com.example.order.domain.model.Order;

public interface OrderRepositoryPort {
    Order save(Order order);
}
