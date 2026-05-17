package com.example.shippingservice.infrastructure.persistence;

import com.example.shippingservice.domain.model.Shipment;
import com.example.shippingservice.domain.repository.ShipmentRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ShipmentRepositoryImpl implements ShipmentRepository {

    private final SpringDataShipmentRepository jpaRepository;

    public ShipmentRepositoryImpl(SpringDataShipmentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Shipment save(Shipment shipment) {
        ShipmentEntity entity = new ShipmentEntity();
        entity.setId(shipment.getId());
        entity.setOrderId(shipment.getOrderId());
        entity.setUserId(shipment.getUserId());
        entity.setAddress(shipment.getAddress());
        entity.setStatus(shipment.getStatus());

        ShipmentEntity savedEntity = jpaRepository.save(entity);

        // Convert back to domain model
        Shipment savedShipment = new Shipment();
        savedShipment.setId(savedEntity.getId());
        savedShipment.setOrderId(savedEntity.getOrderId());
        savedShipment.setUserId(savedEntity.getUserId());
        savedShipment.setAddress(savedEntity.getAddress());
        savedShipment.setStatus(savedEntity.getStatus());

        return savedShipment;
    }

    @Override
    public Optional<Shipment> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(entity -> {
                    Shipment shipment = new Shipment();
                    shipment.setId(entity.getId());
                    shipment.setOrderId(entity.getOrderId());
                    shipment.setUserId(entity.getUserId());
                    shipment.setAddress(entity.getAddress());
                    shipment.setStatus(entity.getStatus());
                    return shipment;
                });
    }
}
