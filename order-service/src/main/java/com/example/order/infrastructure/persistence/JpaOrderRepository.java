package com.example.order.infrastructure.persistence;

import com.example.order.domain.model.Order;
import com.example.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaOrderRepository implements OrderRepository {
    private final SpringDataOrderRepository springDataOrderRepository;

    public JpaOrderRepository(SpringDataOrderRepository springDataOrderRepository) {
        this.springDataOrderRepository = springDataOrderRepository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = OrderEntity.fromDomain(order);
        entity = springDataOrderRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return springDataOrderRepository.findById(id).map(OrderEntity::toDomain);
    }

    @Override
    public List<Order> findByUserId(UUID userId) {
        return springDataOrderRepository.findByUserId(userId).stream()
                .map(OrderEntity::toDomain)
                .collect(Collectors.toList());
    }
}