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
        ShipmentEntity entity = ShipmentEntity.builder()
                .id(shipment.getId())
                .orderId(shipment.getOrderId())
                .paymentCompleted(shipment.isPaymentCompleted())
                .stockReserved(shipment.isStockReserved())
                .shipped(shipment.isShipped())
                .build();
        jpaRepository.save(entity);
        return shipment;
    }

    @Override
    public Optional<Shipment> findByOrderId(String orderId) {
        return jpaRepository.findByOrderId(orderId).map(entity -> Shipment.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .paymentCompleted(entity.isPaymentCompleted())
                .stockReserved(entity.isStockReserved())
                .shipped(entity.isShipped())
                .build());
    }
}
