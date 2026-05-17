package com.microservices.shipping.infrastructure.persistence.repository;

import com.microservices.shipping.domain.model.OrderTracker;
import com.microservices.shipping.domain.repository.OrderTrackerRepository;
import com.microservices.shipping.infrastructure.persistence.mapper.ShippingPersistenceMapper;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class OrderTrackerRepositoryImpl implements OrderTrackerRepository {
    
    private final JpaOrderTrackerRepository jpaRepository;
    
    public OrderTrackerRepositoryImpl(JpaOrderTrackerRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public OrderTracker save(OrderTracker tracker) {
        return ShippingPersistenceMapper.toDomain(
            jpaRepository.save(ShippingPersistenceMapper.toJpaEntity(tracker)));
    }
    
    @Override
    public Optional<OrderTracker> findByOrderId(String orderId) {
        return jpaRepository.findByOrderId(orderId)
            .map(ShippingPersistenceMapper::toDomain);
    }
}
