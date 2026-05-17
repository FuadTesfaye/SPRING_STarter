package com.school.shipping.infrastructure.persistence;

import com.school.shipping.application.port.ShipmentRepository;
import com.school.shipping.domain.entity.Shipment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipmentRepositoryImpl implements ShipmentRepository {

    private final ShipmentJpaRepository jpaRepository;

    @Override
    public Shipment save(Shipment shipment) {
        ShipmentJpaEntity entity = toEntity(shipment);
        return toDomain(jpaRepository.save(entity));
    }

    private ShipmentJpaEntity toEntity(Shipment s) {
        ShipmentJpaEntity e = new ShipmentJpaEntity();
        e.setId(s.getId());
        e.setOrderId(s.getOrderId());
        e.setStudentId(s.getStudentId());
        e.setTrackingNumber(s.getTrackingNumber());
        e.setStatus(s.getStatus());
        e.setCreatedAt(s.getCreatedAt());
        return e;
    }

    private Shipment toDomain(ShipmentJpaEntity e) {
        Shipment s = new Shipment();
        s.setId(e.getId());
        s.setOrderId(e.getOrderId());
        s.setStudentId(e.getStudentId());
        s.setTrackingNumber(e.getTrackingNumber());
        s.setStatus(e.getStatus());
        s.setCreatedAt(e.getCreatedAt());
        return s;
    }
}
