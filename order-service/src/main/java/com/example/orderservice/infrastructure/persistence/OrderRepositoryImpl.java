package com.example.orderservice.infrastructure.persistence;



import com.example.orderservice.domain.model.Order;
import com.example.orderservice.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final SpringDataOrderRepository jpaRepository;

    public OrderRepositoryImpl(SpringDataOrderRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Order save(Order order) {

        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setProductName(order.getProductName());
        entity.setQuantity(order.getQuantity());
        entity.setStatus(order.getStatus());

        OrderEntity saved = jpaRepository.save(entity);

        return new Order(
                saved.getId(),
                saved.getProductName(),
                saved.getQuantity(),
                saved.getStatus()
        );
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(e -> new Order(e.getId(), e.getProductName(), e.getQuantity(), e.getStatus()));
    }

    @Override
    public List<Order> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(e -> new Order(e.getId(), e.getProductName(), e.getQuantity(), e.getStatus()))
                .collect(Collectors.toList());
    }
}
