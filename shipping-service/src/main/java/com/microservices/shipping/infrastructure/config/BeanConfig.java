package com.microservices.shipping.infrastructure.config;

import com.microservices.shipping.application.service.ShippingApplicationService;
import com.microservices.shipping.domain.event.ShippingEventPublisher;
import com.microservices.shipping.domain.repository.OrderTrackerRepository;
import com.microservices.shipping.domain.repository.ShipmentRepository;
import com.microservices.shipping.domain.service.ShippingDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    
    @Bean
    public ShippingDomainService shippingDomainService(
            OrderTrackerRepository trackerRepository,
            ShipmentRepository shipmentRepository,
            ShippingEventPublisher eventPublisher) {
        return new ShippingDomainService(trackerRepository, shipmentRepository, eventPublisher);
    }
    
    @Bean
    public ShippingApplicationService shippingApplicationService(
            ShippingDomainService domainService) {
        return new ShippingApplicationService(domainService);
    }
}
