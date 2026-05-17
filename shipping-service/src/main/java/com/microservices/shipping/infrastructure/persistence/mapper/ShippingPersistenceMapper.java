package com.microservices.shipping.infrastructure.persistence.mapper;

import com.microservices.shipping.domain.model.OrderTracker;
import com.microservices.shipping.domain.model.Shipment;
import com.microservices.shipping.domain.model.ShipmentStatus;
import com.microservices.shipping.infrastructure.persistence.entity.OrderTrackerJpaEntity;
import com.microservices.shipping.infrastructure.persistence.entity.ShipmentJpaEntity;

public final class ShippingPersistenceMapper {
    
    private ShippingPersistenceMapper() {}
    
    // OrderTracker mappings
    public static OrderTrackerJpaEntity toJpaEntity(OrderTracker tracker) {
        if (tracker == null) return null;
        OrderTrackerJpaEntity entity = new OrderTrackerJpaEntity();
        entity.setOrderId(tracker.getOrderId());
        entity.setPaymentCompleted(tracker.isPaymentCompleted());
        entity.setStockReserved(tracker.isStockReserved());
        entity.setPaymentId(tracker.getPaymentId());
        entity.setReservationId(tracker.getReservationId());
        entity.setCreatedAt(tracker.getCreatedAt());
        entity.setUpdatedAt(tracker.getUpdatedAt());
        return entity;
    }
    
    public static OrderTracker toDomain(OrderTrackerJpaEntity entity) {
        if (entity == null) return null;
        OrderTracker tracker = OrderTracker.create(entity.getOrderId());
        if (entity.isPaymentCompleted()) tracker.markPaymentCompleted(entity.getPaymentId());
        if (entity.isStockReserved()) tracker.markStockReserved(entity.getReservationId());
        tracker.setCreatedAt(entity.getCreatedAt());
        tracker.setUpdatedAt(entity.getUpdatedAt());
        return tracker;
    }
    
    // Shipment mappings
    public static ShipmentJpaEntity toJpaEntity(Shipment shipment) {
        if (shipment == null) return null;
        ShipmentJpaEntity entity = new ShipmentJpaEntity();
        entity.setId(shipment.getId());
        entity.setOrderId(shipment.getOrderId());
        entity.setPaymentId(shipment.getPaymentId());
        entity.setReservationId(shipment.getReservationId());
        entity.setTrackingNumber(shipment.getTrackingNumber());
        entity.setStatus(shipment.getStatus().name());
        entity.setCreatedAt(shipment.getCreatedAt());
        return entity;
    }
    
    public static Shipment toDomain(ShipmentJpaEntity entity) {
        if (entity == null) return null;
        Shipment shipment = Shipment.create(entity.getOrderId(), entity.getPaymentId(), entity.getReservationId());
        shipment.setId(entity.getId());
        shipment.setTrackingNumber(entity.getTrackingNumber());
        shipment.setStatus(ShipmentStatus.valueOf(entity.getStatus()));
        shipment.setCreatedAt(entity.getCreatedAt());
        return shipment;
    }
}
