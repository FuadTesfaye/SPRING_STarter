package com.example.shipping.presentation.controller;

import com.example.shipping.application.dto.ShipmentResponse;
import com.example.shipping.application.service.ShippingApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipments")
public class ShippingController {

    private final ShippingApplicationService shippingApplicationService;

    public ShippingController(ShippingApplicationService shippingApplicationService) {
        this.shippingApplicationService = shippingApplicationService;
    }

    @GetMapping("/{shipmentId}")
    public ShipmentResponse getShipment(@PathVariable Long shipmentId) {
        return shippingApplicationService.getShipment(shipmentId);
    }

    @GetMapping("/order/{orderId}")
    public ShipmentResponse getShipmentByOrderId(@PathVariable Long orderId) {
        return shippingApplicationService.getShipmentByOrderId(orderId);
    }
}
