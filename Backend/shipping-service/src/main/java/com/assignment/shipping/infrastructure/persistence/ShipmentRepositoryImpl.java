package com.assignment.shipping.infrastructure.persistence;

import com.assignment.shipping.domain.model.Shipment;
import com.assignment.shipping.domain.repository.ShipmentRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ShipmentRepositoryImpl implements ShipmentRepository {

    private final JpaShipmentRepository jpa;

    public ShipmentRepositoryImpl(JpaShipmentRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Shipment save(Shipment shipment) {
        return jpa.save(ShipmentEntity.fromDomain(shipment)).toDomain();
    }

    @Override
    public Optional<Shipment> findByOrderId(UUID orderId) {
        return jpa.findByOrderId(orderId.toString()).map(ShipmentEntity::toDomain);
    }
}
