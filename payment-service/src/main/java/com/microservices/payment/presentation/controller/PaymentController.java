package com.microservices.payment.presentation.controller;

import com.microservices.payment.application.dto.PaymentResponse;
import com.microservices.payment.application.service.PaymentApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PAYMENT CONTROLLER
 * 
 * WHAT: REST API for querying payments
 * WHY: Provides visibility into payment status
 * 
 * NOTE: Payment processing is event-driven (not via REST)
 * These endpoints are for QUERYING payment results
 */
@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment", description = "Payment query API")
public class PaymentController {
    
    private final PaymentApplicationService paymentApplicationService;
    
    public PaymentController(PaymentApplicationService paymentApplicationService) {
        this.paymentApplicationService = paymentApplicationService;
    }
    
    /**
     * Get payment by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable String id) {
        PaymentResponse response = paymentApplicationService.getPayment(id);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get payments by order ID
     */
    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get payments by order ID")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByOrder(@PathVariable String orderId) {
        List<PaymentResponse> responses = paymentApplicationService.getPaymentsByOrder(orderId);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Get all payments
     */
    @GetMapping
    @Operation(summary = "Get all payments")
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        List<PaymentResponse> responses = paymentApplicationService.getAllPayments();
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Health check
     */
    @GetMapping("/health")
    @Operation(summary = "Health check")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Payment Service is running! 💰");
    }
}
