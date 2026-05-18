package com.example.inventory.application.usecases;

import com.example.inventory.application.ports.InventoryEventPublisher;
import com.example.inventory.domain.model.Stock;
import com.example.inventory.domain.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessInventoryUseCase {
    private final StockRepository stockRepository;
    private final InventoryEventPublisher eventPublisher;

    public void reserveStock(Long orderId, String productId, Integer quantity) {
        System.out.println("Checking stock for Product: " + productId + " Quantity: " + quantity);

        Stock stock = stockRepository.findByProductId(productId)
                .orElse(new Stock(null, productId, 10)); // Default 10 if not found for mock

        if (stock.getQuantity() >= quantity) {
            stock.setQuantity(stock.getQuantity() - quantity);
            stockRepository.save(stock);
            eventPublisher.publishStockReserved(orderId);
        } else {
            eventPublisher.publishStockFailed(orderId, "Not enough stock");
        }
    }
}