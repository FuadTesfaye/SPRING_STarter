package com.example.order.infrastructure.persistence;

import com.example.order.domain.model.Order;
import com.example.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaOrderRepositoryAdapter implements OrderRepository {
    private final SpringDataOrderRepository repository;

    @Override
    public Order save(Order order) {
        OrderEntity entity = new OrderEntity(null, order.getUserId(), order.getProductId(),
                order.getQuantity(), order.getPrice(), order.getStatus());
        OrderEntity saved = repository.save(entity);
        return new Order(saved.getId(), saved.getUserId(), saved.getProductId(),
                saved.getQuantity(), saved.getPrice(), saved.getStatus());
    }
}