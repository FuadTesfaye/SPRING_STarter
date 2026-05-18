package com.assignment.shipping.presentation.controller;

import com.assignment.shipping.application.dto.ShippingRequest;
import com.assignment.shipping.application.dto.ShippingResponse;
import com.assignment.shipping.application.service.ShippingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {
    
    private final ShippingService shippingService;
    
    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<ShippingResponse> createShipment(@RequestBody ShippingRequest request) {
        ShippingResponse response = shippingService.createShipment(request);
        return ResponseEntity.ok(response);
    }
}