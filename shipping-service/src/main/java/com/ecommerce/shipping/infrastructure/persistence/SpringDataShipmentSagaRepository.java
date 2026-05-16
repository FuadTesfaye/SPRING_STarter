package com.ecommerce.shipping.infrastructure.persistence;

import com.ecommerce.shipping.domain.model.ShipmentSaga;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataShipmentSagaRepository extends JpaRepository<ShipmentSaga, String> {
    Optional<ShipmentSaga> findByOrderId(String orderId);
}
