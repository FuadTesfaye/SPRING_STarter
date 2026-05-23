package com.ecom.shipping.infrastructure.persistence;

import com.ecom.shipping.domain.model.Shipment;
import com.ecom.shipping.domain.repository.ShipmentRepository;
import org.springframework.stereotype.Component;

@Component
public class PostgresShipmentRepositoryAdapter implements ShipmentRepository {
    private final JpaShipmentRepository repository;

    public PostgresShipmentRepositoryAdapter(JpaShipmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Shipment save(Shipment shipment) {
        ShipmentEntity entity = new ShipmentEntity(
                shipment.getId(),
                shipment.getOrderId(),
                shipment.getStatus(),
                shipment.getTrackingNumber(),
                shipment.getShippedAt()
        );
        ShipmentEntity saved = repository.save(entity);
        return new Shipment(saved.getId(), saved.getOrderId(), saved.getStatus(), saved.getTrackingNumber(), saved.getShippedAt());
    }

    @Override
    public java.util.List<Shipment> findAll() {
        return repository.findAll().stream()
                .map(entity -> new Shipment(entity.getId(), entity.getOrderId(), entity.getStatus(), entity.getTrackingNumber(), entity.getShippedAt()))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public java.util.Optional<Shipment> findByOrderId(java.util.UUID orderId) {
        return repository.findByOrderId(orderId)
                .map(entity -> new Shipment(entity.getId(), entity.getOrderId(), entity.getStatus(), entity.getTrackingNumber(), entity.getShippedAt()));
    }
}
