package com.app.inventory.infrastructure.config;

import com.app.inventory.application.ports.InventoryEventPublisher;
import com.app.inventory.application.usecases.ReserveStockUseCase;
import com.app.inventory.domain.InventoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ReserveStockUseCase reserveStockUseCase(InventoryRepository inventoryRepository, InventoryEventPublisher eventPublisher) {
        return new ReserveStockUseCase(inventoryRepository, eventPublisher);
    }
}
