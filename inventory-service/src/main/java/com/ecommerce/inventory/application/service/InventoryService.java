package com.ecommerce.inventory.application.service;

import com.ecommerce.inventory.application.port.EventPublisher;
import com.ecommerce.inventory.domain.event.*;
import com.ecommerce.inventory.domain.model.Inventory;
import com.ecommerce.inventory.domain.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final EventPublisher eventPublisher;

    public void checkAndReserve(OrderCreatedEvent event) {
        log.info("[INVENTORY] Checking stock for product: {} qty: {}",
                event.getProductId(), event.getQuantity());

        Inventory inventory = inventoryRepository.findByProductId(event.getProductId())
                .orElse(null);

        if (inventory == null) {
            log.warn("[INVENTORY] Product not found: {}", event.getProductId());
            publishFailed(event, "Product not found in inventory");
            return;
        }

        boolean reserved = inventory.reserve(event.getQuantity());

        if (reserved) {
            inventoryRepository.save(inventory);
            log.info("[INVENTORY] Stock reserved for order: {}", event.getOrderId());
            eventPublisher.publish("stock.reserved", StockReservedEvent.builder()
                    .orderId(event.getOrderId())
                    .productId(event.getProductId())
                    .quantity(event.getQuantity())
                    .timestamp(LocalDateTime.now())
                    .build());
        } else {
            log.warn("[INVENTORY] Insufficient stock for order: {}", event.getOrderId());
            publishFailed(event, "Insufficient stock. Available: " + inventory.getAvailableStock()
                    + ", Requested: " + event.getQuantity());
        }
    }

    private void publishFailed(OrderCreatedEvent event, String reason) {
        eventPublisher.publish("stock.failed", StockFailedEvent.builder()
                .orderId(event.getOrderId())
                .productId(event.getProductId())
                .reason(reason)
                .timestamp(LocalDateTime.now())
                .build());
    }

    public Inventory addStock(String productId, int quantity) {
        Inventory inv = inventoryRepository.findByProductId(productId)
                .orElse(Inventory.builder().productId(productId).availableStock(0).reservedStock(0).build());
        inv.setAvailableStock(inv.getAvailableStock() + quantity);
        return inventoryRepository.save(inv);
    }

    public List<Inventory> findAll() { return inventoryRepository.findAll(); }
}
