package com.school.inventory.infrastructure.persistence;

import com.school.inventory.application.port.StockReservationRepository;
import com.school.inventory.domain.entity.StockReservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockReservationRepositoryImpl implements StockReservationRepository {

    private final StockReservationJpaRepository jpaRepository;

    @Override
    public StockReservation save(StockReservation reservation) {
        StockReservationJpaEntity entity = toEntity(reservation);
        StockReservationJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    private StockReservationJpaEntity toEntity(StockReservation r) {
        StockReservationJpaEntity e = new StockReservationJpaEntity();
        e.setId(r.getId());
        e.setOrderId(r.getOrderId());
        e.setStudentId(r.getStudentId());
        e.setFeeType(r.getFeeType());
        e.setStatus(r.getStatus());
        e.setReservedAt(r.getReservedAt());
        return e;
    }

    private StockReservation toDomain(StockReservationJpaEntity e) {
        StockReservation r = new StockReservation();
        r.setId(e.getId());
        r.setOrderId(e.getOrderId());
        r.setStudentId(e.getStudentId());
        r.setFeeType(e.getFeeType());
        r.setStatus(e.getStatus());
        r.setReservedAt(e.getReservedAt());
        return r;
    }
}
