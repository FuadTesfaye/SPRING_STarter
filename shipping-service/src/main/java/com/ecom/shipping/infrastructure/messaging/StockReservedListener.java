package com.ecom.shipping.infrastructure.messaging;

import com.ecom.shipping.application.dto.StockReservedEvent;
import com.ecom.shipping.application.service.ShipmentCoordinator;

public class StockReservedListener {
    private final ShipmentCoordinator coordinator;

    public StockReservedListener(ShipmentCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    public void handleStockReserved(StockReservedEvent event) {
        coordinator.handleStockReserved(event.orderId());
    }
}
