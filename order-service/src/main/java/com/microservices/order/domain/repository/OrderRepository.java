package com.microservices.order.domain.repository;

import com.microservices.order.domain.model.Order;
import com.microservices.order.domain.model.OrderId;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId id);
    List<Order> findByUserId(String userId);
    List<Order> findAll();
}
