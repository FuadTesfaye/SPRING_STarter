package com.example.shipping.domain.port;

import com.example.shipping.domain.model.Shipping;

public interface ShippingRepositoryPort {
    Shipping save(Shipping shipping);
}
