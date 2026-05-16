package com.example.shipping.infrastructure.persistence;

import com.example.shipping.application.port.out.ShipmentRepository;
import com.example.shipping.domain.model.Shipment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShipmentPersistenceAdapter implements ShipmentRepository {

    private final SpringDataShipmentRepository springDataShipmentRepository;

    public ShipmentPersistenceAdapter(SpringDataShipmentRepository springDataShipmentRepository) {
        this.springDataShipmentRepository = springDataShipmentRepository;
    }

    @Override
    public Shipment save(Shipment shipment) {
        ShipmentEntity entity = new ShipmentEntity(
                shipment.getOrderId(),
                shipment.getShipmentReference(),
                shipment.getStatus(),
                shipment.getCreatedAt(),
                shipment.getUpdatedAt()
        );
        if (shipment.getId() != null) {
            entity.setId(shipment.getId());
        }
        ShipmentEntity saved = springDataShipmentRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Shipment> findByOrderId(Long orderId) {
        return springDataShipmentRepository.findByOrderId(orderId)
                .map(this::toDomain);
    }

    @Override
    public Optional<Shipment> findById(Long id) {
        return springDataShipmentRepository.findById(id)
                .map(this::toDomain);
    }

    private Shipment toDomain(ShipmentEntity entity) {
        return new Shipment(
                entity.getId(),
                entity.getOrderId(),
                entity.getShipmentReference(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
