package com.example.orderservice.domain.interfaces;

import com.example.orderservice.domain.entities.Order;

public interface OrderRepository {

    Order save(Order order);
}
