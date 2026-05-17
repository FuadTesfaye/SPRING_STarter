package com.microservices.inventory.infrastructure.config;

import com.microservices.inventory.application.service.InventoryApplicationService;
import com.microservices.inventory.domain.event.InventoryEventPublisher;
import com.microservices.inventory.domain.repository.StockReservationRepository;
import com.microservices.inventory.domain.service.InventoryDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    
    @Bean
    public InventoryDomainService inventoryDomainService(
            StockReservationRepository repository,
            InventoryEventPublisher eventPublisher) {
        return new InventoryDomainService(repository, eventPublisher);
    }
    
    @Bean
    public InventoryApplicationService inventoryApplicationService(
            InventoryDomainService domainService) {
        return new InventoryApplicationService(domainService);
    }
}
