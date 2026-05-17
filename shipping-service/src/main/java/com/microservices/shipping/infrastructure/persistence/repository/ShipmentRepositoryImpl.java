package com.microservices.shipping.infrastructure.persistence.repository;

import com.microservices.shipping.domain.model.Shipment;
import com.microservices.shipping.domain.repository.ShipmentRepository;
import com.microservices.shipping.infrastructure.persistence.mapper.ShippingPersistenceMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ShipmentRepositoryImpl implements ShipmentRepository {
    
    private final JpaShipmentRepository jpaRepository;
    
    public ShipmentRepositoryImpl(JpaShipmentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public Shipment save(Shipment shipment) {
        return ShippingPersistenceMapper.toDomain(
            jpaRepository.save(ShippingPersistenceMapper.toJpaEntity(shipment)));
    }
    
    @Override
    public Optional<Shipment> findById(String id) {
        return jpaRepository.findById(id).map(ShippingPersistenceMapper::toDomain);
    }
    
    @Override
    public List<Shipment> findAll() {
        return jpaRepository.findAll().stream()
            .map(ShippingPersistenceMapper::toDomain)
            .collect(Collectors.toList());
    }
}
