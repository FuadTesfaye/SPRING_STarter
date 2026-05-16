package com.ecommerce.shipping.domain.repository;

import com.ecommerce.shipping.domain.model.ShipmentSaga;
import java.util.Optional;

public interface ShipmentSagaRepository {
    ShipmentSaga save(ShipmentSaga saga);
    Optional<ShipmentSaga> findByOrderId(String orderId);
}
