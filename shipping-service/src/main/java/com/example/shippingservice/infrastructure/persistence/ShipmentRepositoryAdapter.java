package com.example.shippingservice.infrastructure.persistence;
import com.example.shippingservice.application.port.ShipmentRepositoryPort;
import com.example.shippingservice.domain.model.Shipment;
import org.springframework.stereotype.Repository;
import java.util.Optional; import java.util.UUID;
@Repository
public class ShipmentRepositoryAdapter implements ShipmentRepositoryPort {
    private final ShipmentJpaRepository jpa;
    public ShipmentRepositoryAdapter(ShipmentJpaRepository j) { jpa=j; }
    @Override public Shipment save(Shipment s) {
        ShipmentEntity e = new ShipmentEntity();
        e.setId(s.getId()); e.setOrderId(s.getOrderId());
        e.setStatus(s.getStatus()); e.setCreatedAt(s.getCreatedAt());
        jpa.save(e); return s;
    }
    @Override public Optional<Shipment> findByOrderId(UUID orderId) {
        return jpa.findByOrderId(orderId).map(e ->
            new Shipment(e.getId(), e.getOrderId(), e.getStatus(), e.getCreatedAt()));
    }
}
