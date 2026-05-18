package com.app.shipping.infrastructure.config;

import com.app.shipping.application.ports.ShippingEventPublisher;
import com.app.shipping.application.usecases.CreateShipmentUseCase;
import com.app.shipping.domain.ShipmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CreateShipmentUseCase createShipmentUseCase(ShipmentRepository shipmentRepository, ShippingEventPublisher eventPublisher) {
        return new CreateShipmentUseCase(shipmentRepository, eventPublisher);
    }
}
