package com.ecom.inventory.presentation.rest;

import com.ecom.inventory.application.dto.OrderCreatedEvent;
import com.ecom.inventory.application.service.InventoryManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventWebhookController {

    private final InventoryManager inventoryManager;

    public EventWebhookController(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @PostMapping("/order-created")
    public ResponseEntity<String> handleOrderCreated(@RequestBody OrderCreatedEvent event) {
        inventoryManager.handleOrderCreated(event);
        return ResponseEntity.ok("INVENTORY_RESERVED");
    }
}
