package com.ecom.shipping.presentation.rest;

import com.ecom.shipping.application.service.ShipmentCoordinator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventWebhookController {

    private final ShipmentCoordinator coordinator;

    public EventWebhookController(ShipmentCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    @PostMapping("/payment-completed")
    public ResponseEntity<String> handlePaymentCompleted(@RequestBody EventRequest request) {
        coordinator.handlePaymentCompleted(request.getOrderId());
        return ResponseEntity.ok("PAYMENT_COMPLETED_PROCESSED");
    }

    @PostMapping("/stock-reserved")
    public ResponseEntity<String> handleStockReserved(@RequestBody EventRequest request) {
        coordinator.handleStockReserved(request.getOrderId());
        return ResponseEntity.ok("STOCK_RESERVED_PROCESSED");
    }

    public static class EventRequest {
        private UUID orderId;
        public UUID getOrderId() { return orderId; }
        public void setOrderId(UUID orderId) { this.orderId = orderId; }
    }
}
