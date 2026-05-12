package com.microservices.shippingservice.infrastructure.persistence;

import com.microservices.shippingservice.domain.model.Shipping;
import com.microservices.shippingservice.domain.port.ShippingRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class ShippingRepositoryAdapter
        implements ShippingRepositoryPort {

    private final ShippingJpaRepository repository;

    public ShippingRepositoryAdapter(
            ShippingJpaRepository repository) {

        this.repository = repository;
    }

    @Override
    public Shipping save(Shipping shipping) {

        ShippingEntity entity =
                new ShippingEntity();

        entity.setUsername(shipping.getUsername());
        entity.setStatus(shipping.getStatus());

        repository.save(entity);

        return shipping;
    }
}