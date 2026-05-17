package com.example.shipmentservice.infrastructure.config;

import com.example.shipmentservice.application.usecases.CreateShipmentService;
import com.example.shipmentservice.application.usecases.CreateShipmentUseCase;
import com.example.shipmentservice.domain.interfaces.ShipmentRepository;
import com.example.shipmentservice.domain.services.ShipmentDomainService;
import com.example.shipmentservice.infrastructure.persistence.adapter.ShipmentPersistenceAdapter;
import com.example.shipmentservice.infrastructure.persistence.adapter.ShipmentPersistenceMapper;
import com.example.shipmentservice.infrastructure.persistence.repository.SpringDataShipmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShipmentConfiguration {

    @Bean
    public ShipmentDomainService shipmentDomainService() {
        return new ShipmentDomainService();
    }

    @Bean
    public ShipmentPersistenceMapper shipmentPersistenceMapper() {
        return new ShipmentPersistenceMapper();
    }

    @Bean
    public ShipmentRepository shipmentRepository(
            SpringDataShipmentRepository springDataShipmentRepository,
            ShipmentPersistenceMapper shipmentPersistenceMapper
    ) {
        return new ShipmentPersistenceAdapter(springDataShipmentRepository, shipmentPersistenceMapper);
    }

    @Bean
    public CreateShipmentService createShipmentUseCase(
            ShipmentRepository shipmentRepository,
            ShipmentDomainService shipmentDomainService
    ) {
        return new CreateShipmentUseCase(shipmentRepository, shipmentDomainService);
    }
}
