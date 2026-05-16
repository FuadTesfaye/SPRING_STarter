package com.ecommerce.shipping.infrastructure.persistence;

import com.ecommerce.shipping.domain.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataShipmentRepository extends JpaRepository<Shipment, String> {
    Optional<Shipment> findByOrderId(String orderId);
}
