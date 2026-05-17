package com.example.shipping.infrastructure.persistence;

import com.example.shipping.domain.model.Shipment;
import com.example.shipping.domain.repository.ShipmentRepository;
import org.springframework.stereotype.Component;

@Component
public class JpaShipmentRepository implements ShipmentRepository {
    private final SpringDataShipmentRepository springDataShipmentRepository;

    public JpaShipmentRepository(SpringDataShipmentRepository springDataShipmentRepository) {
        this.springDataShipmentRepository = springDataShipmentRepository;
    }

    @Override
    public Shipment save(Shipment shipment) {
        ShipmentEntity entity = ShipmentEntity.fromDomain(shipment);
        entity = springDataShipmentRepository.save(entity);
        return entity.toDomain();
    }
}