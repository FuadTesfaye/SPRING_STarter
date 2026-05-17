package com.app.shipping.infrastructure.persistence;

import com.app.shipping.domain.Shipment;
import com.app.shipping.domain.ShipmentRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ShipmentRepositoryImpl implements ShipmentRepository {
    private final SpringDataShipmentRepository repository;

    public ShipmentRepositoryImpl(SpringDataShipmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Shipment save(Shipment shipment) {
        ShipmentEntity entity = new ShipmentEntity(
                shipment.getId(),
                shipment.getOrderId(),
                shipment.getTrackingNumber(),
                shipment.isPaymentReceived(),
                shipment.isStockReserved()
        );
        ShipmentEntity saved = repository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Optional<Shipment> findByOrderId(UUID orderId) {
        return repository.findByOrderId(orderId).map(this::mapToDomain);
    }

    private Shipment mapToDomain(ShipmentEntity entity) {
        Shipment shipment = new Shipment(entity.getId(), entity.getOrderId(), entity.getTrackingNumber());
        shipment.setPaymentReceived(entity.isPaymentReceived());
        shipment.setStockReserved(entity.isStockReserved());
        return shipment;
    }
}
