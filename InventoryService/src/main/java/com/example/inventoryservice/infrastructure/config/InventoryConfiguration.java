package com.example.inventoryservice.infrastructure.config;

import com.example.inventoryservice.application.usecases.GetInventoryService;
import com.example.inventoryservice.application.usecases.GetInventoryUseCase;
import com.example.inventoryservice.application.usecases.ReserveInventoryService;
import com.example.inventoryservice.application.usecases.ReserveInventoryUseCase;
import com.example.inventoryservice.domain.interfaces.InventoryRepository;
import com.example.inventoryservice.domain.services.InventoryDomainService;
import com.example.inventoryservice.infrastructure.persistence.adapter.InventoryPersistenceAdapter;
import com.example.inventoryservice.infrastructure.persistence.adapter.InventoryPersistenceMapper;
import com.example.inventoryservice.infrastructure.persistence.entity.InventoryItemJpaEntity;
import com.example.inventoryservice.infrastructure.persistence.repository.SpringDataInventoryRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryConfiguration {

    @Bean
    public InventoryDomainService inventoryDomainService() {
        return new InventoryDomainService();
    }

    @Bean
    public InventoryPersistenceMapper inventoryPersistenceMapper() {
        return new InventoryPersistenceMapper();
    }

    @Bean
    public InventoryRepository inventoryRepository(
            SpringDataInventoryRepository springDataInventoryRepository,
            InventoryPersistenceMapper inventoryPersistenceMapper
    ) {
        return new InventoryPersistenceAdapter(springDataInventoryRepository, inventoryPersistenceMapper);
    }

    @Bean
    public GetInventoryService getInventoryUseCase(InventoryRepository inventoryRepository) {
        return new GetInventoryUseCase(inventoryRepository);
    }

    @Bean
    public ReserveInventoryService reserveInventoryUseCase(
            InventoryRepository inventoryRepository,
            InventoryDomainService inventoryDomainService
    ) {
        return new ReserveInventoryUseCase(inventoryRepository, inventoryDomainService);
    }

    @Bean
    public ApplicationRunner seedInventory(SpringDataInventoryRepository seedRepository) {
        return args -> {
            if (seedRepository.count() == 0) {
                seedRepository.save(createItem(1L, 100));
                seedRepository.save(createItem(2L, 75));
                seedRepository.save(createItem(3L, 50));
            }
        };
    }

    private InventoryItemJpaEntity createItem(Long productId, int quantity) {
        InventoryItemJpaEntity entity = new InventoryItemJpaEntity();
        entity.setProductId(productId);
        entity.setAvailableQuantity(quantity);
        return entity;
    }
}
