package com.ecommerce.order.domain.repository;

import com.ecommerce.order.domain.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(String id);
    List<Order> findByUserId(String userId);
    List<Order> findAll();
}
