package com.microservices.inventory.infrastructure.persistence.repository;

import com.microservices.inventory.domain.model.StockReservation;
import com.microservices.inventory.domain.repository.StockReservationRepository;
import com.microservices.inventory.infrastructure.persistence.entity.StockReservationJpaEntity;
import com.microservices.inventory.infrastructure.persistence.mapper.StockPersistenceMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StockReservationRepositoryImpl implements StockReservationRepository {
    
    private final JpaStockReservationRepository jpaRepository;
    
    public StockReservationRepositoryImpl(JpaStockReservationRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public StockReservation save(StockReservation reservation) {
        StockReservationJpaEntity entity = StockPersistenceMapper.toJpaEntity(reservation);
        StockReservationJpaEntity saved = jpaRepository.save(entity);
        return StockPersistenceMapper.toDomain(saved);
    }
    
    @Override
    public Optional<StockReservation> findById(String id) {
        return jpaRepository.findById(id).map(StockPersistenceMapper::toDomain);
    }
    
    @Override
    public List<StockReservation> findByOrderId(String orderId) {
        return jpaRepository.findByOrderId(orderId).stream()
            .map(StockPersistenceMapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<StockReservation> findAll() {
        return jpaRepository.findAll().stream()
            .map(StockPersistenceMapper::toDomain)
            .collect(Collectors.toList());
    }
}
