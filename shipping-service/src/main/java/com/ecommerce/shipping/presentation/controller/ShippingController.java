package com.ecommerce.shipping.presentation.controller;

import com.ecommerce.shipping.application.service.ShippingService;
import com.ecommerce.shipping.domain.model.Shipment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
@Tag(name = "Shipments", description = "Shipment tracking endpoints")
public class ShippingController {

    private final ShippingService shippingService;

    @GetMapping
    @Operation(summary = "Get all shipments")
    public ResponseEntity<List<Shipment>> getAll() {
        return ResponseEntity.ok(shippingService.findAll());
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get shipment by order ID")
    public ResponseEntity<Shipment> getByOrder(@PathVariable String orderId) {
        return shippingService.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
