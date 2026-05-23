package com.assignment.order.infrastructure.persistence;

import com.assignment.order.domain.model.Order;
import com.assignment.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final JpaOrderRepository jpa;

    public OrderRepositoryImpl(JpaOrderRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Order save(Order order) {
        return jpa.save(OrderEntity.fromDomain(order)).toDomain();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpa.findById(id.toString()).map(OrderEntity::toDomain);
    }

    @Override
    public List<Order> findByUserId(UUID userId) {
        return jpa.findByUserId(userId.toString()).stream().map(OrderEntity::toDomain).toList();
    }
}
