package com.school.payment.presentation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payments", description = "Payment service - driven by events")
public class PaymentController {

    @GetMapping("/health")
    public String health() {
        return "Payment Service is running. Listening on RabbitMQ for order.created events.";
    }
}
