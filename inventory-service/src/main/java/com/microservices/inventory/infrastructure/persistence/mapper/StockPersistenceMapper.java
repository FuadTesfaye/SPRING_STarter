package com.microservices.inventory.infrastructure.persistence.mapper;

import com.microservices.inventory.domain.model.StockReservation;
import com.microservices.inventory.domain.model.StockStatus;
import com.microservices.inventory.infrastructure.persistence.entity.StockReservationJpaEntity;

public final class StockPersistenceMapper {
    
    private StockPersistenceMapper() {}
    
    public static StockReservationJpaEntity toJpaEntity(StockReservation reservation) {
        if (reservation == null) return null;
        StockReservationJpaEntity entity = new StockReservationJpaEntity();
        entity.setId(reservation.getId());
        entity.setOrderId(reservation.getOrderId());
        entity.setProductId(reservation.getProductId());
        entity.setProductName(reservation.getProductName());
        entity.setQuantity(reservation.getQuantity());
        entity.setStatus(reservation.getStatus().name());
        entity.setReason(reservation.getReason());
        entity.setProcessedAt(reservation.getProcessedAt());
        return entity;
    }
    
    public static StockReservation toDomain(StockReservationJpaEntity entity) {
        if (entity == null) return null;
        return StockReservation.reconstruct(
            entity.getId(), entity.getOrderId(), entity.getProductId(),
            entity.getProductName(), entity.getQuantity(),
            StockStatus.valueOf(entity.getStatus()), entity.getReason(),
            entity.getProcessedAt()
        );
    }
}
