package com.company.shipping.infrastructure.persistence.repository;

import com.company.shipping.domain.model.Shipment;
import com.company.shipping.domain.repository.IShippingRepository;
import com.company.shipping.infrastructure.persistence.entity.ShipmentEntity;
import org.springframework.stereotype.Component;

@Component
public class ShippingPersistenceAdapter implements IShippingRepository {
    private final SpringDataShippingRepository repository;

    public ShippingPersistenceAdapter(SpringDataShippingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Shipment save(Shipment shipment) {
        ShipmentEntity entity = ShipmentEntity.fromDomain(shipment);
        ShipmentEntity saved = repository.save(entity);
        return saved.toDomain();
    }
}
