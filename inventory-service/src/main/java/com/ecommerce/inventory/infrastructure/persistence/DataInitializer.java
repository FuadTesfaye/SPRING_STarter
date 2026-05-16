package com.ecommerce.inventory.infrastructure.persistence;

import com.ecommerce.inventory.domain.model.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds initial inventory data on startup.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final SpringDataInventoryRepository repo;

    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            repo.save(Inventory.builder().productId("PRODUCT-001").availableStock(100).reservedStock(0).build());
            repo.save(Inventory.builder().productId("PRODUCT-002").availableStock(50).reservedStock(0).build());
            repo.save(Inventory.builder().productId("PRODUCT-003").availableStock(200).reservedStock(0).build());
            repo.save(Inventory.builder().productId("PRODUCT-004").availableStock(5).reservedStock(0).build());
            log.info("[INVENTORY] Seeded 4 products with initial stock");
        }
    }
}
