package com.example.inventory.application.service;

import com.example.inventory.application.dto.OrderStockCommand;
import com.example.inventory.domain.service.StockRegistry;
import com.example.inventory.infrastructure.messaging.InventoryEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryForOrderApplicationService {


    private final StockRegistry stockRegistry;

    public void reserveStockForOrder(OrderStockCommand cmd) {
        log.info("Checking stock for order {}", cmd.orderId());
        /*try {
            boolean ok = stockRegistry.tryReserve(cmd.productId(), cmd.quantity());
            if (!ok) {
                events.publishFailed(cmd.orderId(), "Insufficient stock or invalid SKU/qty");
                return;
            }
            events.publishReserved(cmd.orderId(), cmd.productId(), cmd.quantity());
        } catch (Exception ex) {
            log.error("Inventory error for {}", cmd.orderId(), ex);
            events.publishFailed(cmd.orderId(), ex.getMessage());
        }*/
    }
}
