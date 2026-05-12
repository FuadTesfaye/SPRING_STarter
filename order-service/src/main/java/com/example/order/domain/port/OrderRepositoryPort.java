package com.microservices.orderservice.domain.port;

import com.microservices.orderservice.domain.model.Order;

public interface OrderRepositoryPort {

    Order save(Order order);
}