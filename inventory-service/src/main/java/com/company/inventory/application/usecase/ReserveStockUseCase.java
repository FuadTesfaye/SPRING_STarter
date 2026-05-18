package com.company.inventory.application.usecase;

import com.company.inventory.domain.model.ProductStock;
import com.company.inventory.domain.repository.IInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReserveStockUseCase {
    private final IInventoryRepository inventoryRepository;

    @Transactional
    public void execute(String productId, Integer quantity) {
        ProductStock stock = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product not found in inventory"));

        stock.deductStock(quantity);
        inventoryRepository.save(stock);
        System.out.println("Stock reserved for product: " + productId);
    }
}
