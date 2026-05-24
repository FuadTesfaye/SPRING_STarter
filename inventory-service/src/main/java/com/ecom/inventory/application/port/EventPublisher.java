package com.ecom.inventory.application.port;

import com.ecom.inventory.application.dto.StockReservedEvent;

public interface EventPublisher {
    void publishStockReserved(StockReservedEvent event);
}
