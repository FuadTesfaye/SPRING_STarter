package com.ecom.shipping.domain.repository;

import com.ecom.shipping.domain.model.ShippingCorrelation;
import java.util.Optional;
import java.util.UUID;

public interface ShippingCorrelationRepository {
    Optional<ShippingCorrelation> findByOrderId(UUID orderId);
    ShippingCorrelation save(ShippingCorrelation correlation);
}
