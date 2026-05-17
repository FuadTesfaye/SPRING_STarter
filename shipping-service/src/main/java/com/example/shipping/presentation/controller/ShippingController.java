package com.example.shipping.presentation.controller;

import com.example.shipping.application.service.ShippingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @PostMapping("/create")
    public String createShipping(@RequestParam String username) {
        shippingService.createShipping(username);
        return "Shipping created for: " + username;
    }
}
