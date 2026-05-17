package com.example.shipmentservice.infrastructure.persistence.adapter;

import com.example.shipmentservice.domain.entities.Shipment;
import com.example.shipmentservice.domain.interfaces.ShipmentRepository;
import com.example.shipmentservice.infrastructure.persistence.repository.SpringDataShipmentRepository;

public class ShipmentPersistenceAdapter implements ShipmentRepository {

    private final SpringDataShipmentRepository springDataShipmentRepository;
    private final ShipmentPersistenceMapper shipmentPersistenceMapper;

    public ShipmentPersistenceAdapter(
            SpringDataShipmentRepository springDataShipmentRepository,
            ShipmentPersistenceMapper shipmentPersistenceMapper
    ) {
        this.springDataShipmentRepository = springDataShipmentRepository;
        this.shipmentPersistenceMapper = shipmentPersistenceMapper;
    }

    @Override
    public Shipment save(Shipment shipment) {
        return shipmentPersistenceMapper.toDomain(
                springDataShipmentRepository.save(shipmentPersistenceMapper.toEntity(shipment))
        );
    }
}
