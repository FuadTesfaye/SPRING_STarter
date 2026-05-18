package com.app.inventory.infrastructure.persistence;

import com.app.inventory.domain.InventoryRepository;
import org.springframework.stereotype.Repository;
import java.util.Random;

@Repository
public class InventoryRepositoryImpl implements InventoryRepository {
    private final Random random = new Random();

    @Override
    public boolean checkAndReserve(String sku, int quantity) {
        // Simulating stock check
        return random.nextDouble() > 0.2; // 80% success rate
    }
}
