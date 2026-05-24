package com.ecom.inventory.application.service;

import com.ecom.inventory.application.dto.OrderCreatedEvent;
import com.ecom.inventory.application.dto.OrderItemDetail;
import com.ecom.inventory.application.dto.StockReservedEvent;
import com.ecom.inventory.application.port.EventPublisher;
import com.ecom.inventory.domain.model.Stock;
import com.ecom.inventory.domain.repository.InventoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryManager {
    private final InventoryRepository inventoryRepository;
    private final EventPublisher eventPublisher;

    public InventoryManager(InventoryRepository inventoryRepository, EventPublisher eventPublisher) {
        this.inventoryRepository = inventoryRepository;
        this.eventPublisher = eventPublisher;
    }

    public void handleOrderCreated(OrderCreatedEvent event) {
        boolean allAvailable = true;
        List<Stock> stocksToUpdate = new ArrayList<>();

        for (OrderItemDetail item : event.items()) {
            Optional<Stock> stockOpt = inventoryRepository.findByProductId(item.productId());
            if (stockOpt.isPresent() && stockOpt.get().hasEnoughStock(item.quantity())) {
                Stock stock = stockOpt.get();
                stock.reserve(item.quantity());
                stocksToUpdate.add(stock);
            } else {
                allAvailable = false;
                break;
            }
        }

        String status;
        if (allAvailable) {
            stocksToUpdate.forEach(inventoryRepository::save);
            status = "RESERVED";
        } else {
            status = "FAILED";
        }

        eventPublisher.publishStockReserved(new StockReservedEvent(
                event.orderId(),
                status
        ));
    }
}
