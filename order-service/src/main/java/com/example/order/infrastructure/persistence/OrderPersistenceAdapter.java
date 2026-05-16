package com.example.order.infrastructure.persistence;

import com.example.order.application.port.out.OrderRepository;
import com.example.order.domain.model.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderPersistenceAdapter implements OrderRepository {

    private final SpringDataOrderRepository repository;

    public OrderPersistenceAdapter(SpringDataOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setUserId(order.getUserId());
        entity.setProductId(order.getProductId());
        entity.setQuantity(order.getQuantity());
        entity.setTotalAmount(order.getTotalAmount());
        entity.setStatus(order.getStatus());
        entity.setCreatedAt(order.getCreatedAt());

        return toDomain(repository.save(entity));
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    private Order toDomain(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getUserId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getTotalAmount(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
