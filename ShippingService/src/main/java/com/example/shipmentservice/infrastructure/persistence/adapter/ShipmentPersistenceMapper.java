package com.example.shipmentservice.infrastructure.persistence.adapter;

import com.example.shipmentservice.domain.entities.Shipment;
import com.example.shipmentservice.domain.enums.ShipmentStatus;
import com.example.shipmentservice.infrastructure.persistence.entity.ShipmentJpaEntity;

public class ShipmentPersistenceMapper {

    public ShipmentJpaEntity toEntity(Shipment shipment) {
        ShipmentJpaEntity entity = new ShipmentJpaEntity();
        entity.setShipmentId(shipment.shipmentId());
        entity.setOrderId(shipment.orderId());
        entity.setProductId(shipment.productId());
        entity.setQuantity(shipment.quantity());
        entity.setStatus(shipment.status().name());
        return entity;
    }

    public Shipment toDomain(ShipmentJpaEntity entity) {
        return new Shipment(
                entity.getShipmentId(),
                entity.getOrderId(),
                entity.getProductId(),
                entity.getQuantity(),
                ShipmentStatus.valueOf(entity.getStatus())
        );
    }
}
