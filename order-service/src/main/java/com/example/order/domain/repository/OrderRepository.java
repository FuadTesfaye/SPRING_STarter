package com.example.order.domain.repository;
import com.example.order.domain.model.Order;

public interface OrderRepository {
    Order save(Order order);
}

