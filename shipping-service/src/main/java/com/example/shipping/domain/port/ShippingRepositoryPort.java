package com.microservices.shippingservice.domain.port;

import com.microservices.shippingservice.domain.model.Shipping;

public interface ShippingRepositoryPort {

    Shipping save(Shipping shipping);
}