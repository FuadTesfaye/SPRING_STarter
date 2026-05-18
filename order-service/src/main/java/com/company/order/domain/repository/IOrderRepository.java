package com.company.order.domain.repository;

import com.company.order.domain.model.Order;
import java.util.Optional;

public interface IOrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);
}
