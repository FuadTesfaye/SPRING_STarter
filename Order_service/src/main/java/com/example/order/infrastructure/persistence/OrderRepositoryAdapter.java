package com.example.order.infrastructure.persistence;

import com.example.order.domain.model.Order;
import com.example.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderRepository jpa;

    @Override
    public Order save(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setOrderId(order.getOrderId());
        entity.setProductId(order.getProductId());
        entity.setQuantity(order.getQuantity());
        entity.setTotalPrice(order.getTotalPrice());
        entity.setStatus(order.getStatus());
        return toDomain(jpa.save(entity));
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return jpa.findById(orderId).map(this::toDomain);
    }

    private Order toDomain(OrderEntity e) {
        return new Order(e.getOrderId(), e.getProductId(), e.getQuantity(), e.getTotalPrice(), e.getStatus());
    }
}
