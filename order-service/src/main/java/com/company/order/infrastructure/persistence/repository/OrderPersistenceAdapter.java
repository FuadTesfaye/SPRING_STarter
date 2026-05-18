package com.company.order.infrastructure.persistence.repository;

import com.company.order.domain.model.Order;
import com.company.order.domain.repository.IOrderRepository;
import com.company.order.infrastructure.persistence.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements IOrderRepository {

    private final SpringDataOrderRepository repository;

    @Override
    public Order save(Order order) {
        OrderEntity entity = OrderEntity.fromDomain(order);
        OrderEntity saved = repository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id).map(OrderEntity::toDomain);
    }
}
