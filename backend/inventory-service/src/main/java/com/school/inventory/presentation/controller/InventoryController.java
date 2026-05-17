package com.school.inventory.presentation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory", description = "Inventory service - driven by events")
public class InventoryController {

    @GetMapping("/health")
    public String health() {
        return "Inventory Service is running. Listening on RabbitMQ for order.created events.";
    }
}
