package com.ecom.inventory.infrastructure.config;

import com.ecom.inventory.application.port.EventPublisher;
import com.ecom.inventory.application.service.InventoryManager;
import com.ecom.inventory.domain.repository.InventoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public InventoryManager inventoryManager(InventoryRepository inventoryRepository, EventPublisher eventPublisher) {
        return new InventoryManager(inventoryRepository, eventPublisher);
    }
}
