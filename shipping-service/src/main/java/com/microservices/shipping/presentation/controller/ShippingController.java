package com.microservices.shipping.presentation.controller;

import com.microservices.shipping.application.dto.ShipmentResponse;
import com.microservices.shipping.application.service.ShippingApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping")
@Tag(name = "Shipping", description = "Shipping query API")
public class ShippingController {
    
    private final ShippingApplicationService applicationService;
    
    public ShippingController(ShippingApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get shipment by ID")
    public ResponseEntity<ShipmentResponse> getShipment(@PathVariable String id) {
        return ResponseEntity.ok(applicationService.getShipment(id));
    }
    
    @GetMapping
    @Operation(summary = "Get all shipments")
    public ResponseEntity<List<ShipmentResponse>> getAllShipments() {
        return ResponseEntity.ok(applicationService.getAllShipments());
    }
    
    @GetMapping("/health")
    @Operation(summary = "Health check")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Shipping Service is running! 🚚");
    }
}
