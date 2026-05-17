package com.example.shipping.infrastructure.persisitence;

import com.example.shipping.domain.model.Shipping;
import com.example.shipping.domain.port.ShippingRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class ShippingRepositoryAdapter implements ShippingRepositoryPort {

    private final ShippingJpaRepository repository;

    public ShippingRepositoryAdapter(ShippingJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Shipping save(Shipping shipping) {
        ShippingEntity entity = new ShippingEntity();
        entity.setUsername(shipping.getUsername());
        entity.setStatus(shipping.getStatus());
        repository.save(entity);
        return shipping;
    }
}
