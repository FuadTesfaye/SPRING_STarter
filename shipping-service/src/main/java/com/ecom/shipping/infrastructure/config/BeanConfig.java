package com.ecom.shipping.infrastructure.config;

import com.ecom.shipping.application.port.EventPublisher;
import com.ecom.shipping.application.service.ShipmentCoordinator;
import com.ecom.shipping.domain.repository.ShipmentRepository;
import com.ecom.shipping.domain.repository.ShippingCorrelationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ShipmentCoordinator shipmentCoordinator(ShippingCorrelationRepository correlationRepository, 
                                                   ShipmentRepository shipmentRepository, 
                                                   EventPublisher eventPublisher) {
        return new ShipmentCoordinator(correlationRepository, shipmentRepository, eventPublisher);
    }
}
