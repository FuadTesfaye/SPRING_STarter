package com.ecom.order.infrastructure.persistence;

import com.ecom.order.domain.model.Order;
import com.ecom.order.domain.model.OrderItem;
import com.ecom.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostgresOrderRepositoryAdapter implements OrderRepository {
    private final JpaOrderRepository repository;

    public PostgresOrderRepositoryAdapter(JpaOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = new OrderEntity(
                order.getId(),
                order.getCustomerId(),
                order.getItems().stream()
                        .map(item -> new OrderItemEntity(item.productId(), item.productName(), item.quantity(), item.price()))
                        .collect(Collectors.toList()),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
        OrderEntity saved = repository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return repository.findById(id).map(this::mapToDomain);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    private Order mapToDomain(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getCustomerId(),
                entity.getItems().stream()
                        .map(item -> new OrderItem(item.getProductId(), item.getProductName(), item.getQuantity(), item.getPrice()))
                        .collect(Collectors.toList()),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
