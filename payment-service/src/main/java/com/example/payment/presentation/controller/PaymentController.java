package com.example.payment.presentation.controller;

import com.example.payment.application.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payments", description = "Payment processing endpoints")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Process a payment for a user",
        responses = {
            @ApiResponse(responseCode = "200", description = "Payment processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
        })
    @PostMapping("/process")
    public String processPayment(@RequestParam(name = "username") String username) {
        paymentService.processPayment(username);
        return "Payment processed for: " + username;
    }

    @Operation(summary = "Check payment service status")
    @GetMapping
    public String status() {
        return "Payment Service Running";
    }
}
