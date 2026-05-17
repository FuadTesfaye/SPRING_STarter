package com.microservices.order.infrastructure.persistence.repository;

import com.microservices.order.domain.model.Order;
import com.microservices.order.domain.model.OrderId;
import com.microservices.order.domain.repository.OrderRepository;
import com.microservices.order.infrastructure.persistence.entity.OrderJpaEntity;
import com.microservices.order.infrastructure.persistence.mapper.OrderPersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REPOSITORY IMPLEMENTATION: OrderRepositoryImpl
 * 
 * WHAT: Implements domain's OrderRepository interface using JPA
 * WHY: Domain defines WHAT, this implements HOW
 * 
 * ADAPTER PATTERN:
 * Converts between domain objects and JPA entities
 * Domain code never touches JPA!
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {
    
    private final JpaOrderRepository jpaOrderRepository;
    
    public OrderRepositoryImpl(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }
    
    @Override
    public Order save(Order order) {
        // Convert domain → JPA
        OrderJpaEntity entity = OrderPersistenceMapper.toJpaEntity(order);
        
        // Save using Spring Data
        OrderJpaEntity savedEntity = jpaOrderRepository.save(entity);
        
        // Convert back → domain
        return OrderPersistenceMapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Order> findById(OrderId id) {
        return jpaOrderRepository.findById(id.getValue())
            .map(OrderPersistenceMapper::toDomain);
    }
    
    @Override
    public List<Order> findByUserId(String userId) {
        return jpaOrderRepository.findByUserId(userId).stream()
            .map(OrderPersistenceMapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Order> findAll() {
        return jpaOrderRepository.findAll().stream()
            .map(OrderPersistenceMapper::toDomain)
            .collect(Collectors.toList());
    }
}
