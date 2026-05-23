package com.example.shippingservice.application.port;
import com.example.shippingservice.domain.model.Shipment;
import java.util.Optional; import java.util.UUID;
public interface ShipmentRepositoryPort {
    Shipment save(Shipment s);
    Optional<Shipment> findByOrderId(UUID orderId);
}
