package com.example.order.application.port.out;

import com.example.order.domain.model.Order;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long id);
}
