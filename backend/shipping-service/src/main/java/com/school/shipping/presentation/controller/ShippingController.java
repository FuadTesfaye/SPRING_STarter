package com.school.shipping.presentation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipping")
@Tag(name = "Shipping", description = "Shipping service - driven by events")
public class ShippingController {

    @GetMapping("/health")
    public String health() {
        return "Shipping Service is running. Listening for payment.completed and stock.reserved events.";
    }
}
