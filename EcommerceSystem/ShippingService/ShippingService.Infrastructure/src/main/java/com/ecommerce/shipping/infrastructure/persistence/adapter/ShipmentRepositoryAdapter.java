package com.ecommerce.shipping.infrastructure.persistence.adapter;

import com.ecommerce.shipping.domain.entity.Shipment;
import com.ecommerce.shipping.domain.repository.ShipmentRepository;
import com.ecommerce.shipping.infrastructure.persistence.entity.ShipmentEntity;
import com.ecommerce.shipping.infrastructure.persistence.repository.ShipmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ShipmentRepositoryAdapter implements ShipmentRepository {

    private final ShipmentJpaRepository jpaRepository;

    @Override
    public Shipment save(Shipment shipment) {
        ShipmentEntity entity = toEntity(shipment);
        ShipmentEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Shipment> findByOrderId(String orderId) {
        return jpaRepository.findByOrderId(orderId).map(this::toDomain);
    }

    private ShipmentEntity toEntity(Shipment shipment) {
        return ShipmentEntity.builder()
                .id(shipment.getId())
                .orderId(shipment.getOrderId())
                .trackingNumber(shipment.getTrackingNumber())
                .status(shipment.getStatus() != null ? shipment.getStatus().name() : null)
                .estimatedDelivery(shipment.getEstimatedDelivery())
                .build();
    }

    private Shipment toDomain(ShipmentEntity entity) {
        return Shipment.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .trackingNumber(entity.getTrackingNumber())
                .status(entity.getStatus() != null ? Shipment.ShipmentStatus.valueOf(entity.getStatus()) : null)
                .estimatedDelivery(entity.getEstimatedDelivery())
                .build();
    }
}
