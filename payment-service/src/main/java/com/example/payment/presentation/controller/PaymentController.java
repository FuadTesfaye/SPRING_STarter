package com.example.payment.presentation.controller;

import com.example.payment.application.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public String processPayment(@RequestParam String username) {
        paymentService.processPayment(username);
        return "Payment processed for: " + username;
    }
}
