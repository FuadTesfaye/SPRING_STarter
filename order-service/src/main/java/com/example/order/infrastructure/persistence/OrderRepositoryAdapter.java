package com.example.order.infrastructure.persistence;

import com.example.order.domain.model.Order;
import com.example.order.domain.port.OrderRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository repository;

    public OrderRepositoryAdapter(OrderJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setUsername(order.getUsername());
        entity.setStatus(order.getStatus());
        repository.save(entity);
        return order;
    }
}
