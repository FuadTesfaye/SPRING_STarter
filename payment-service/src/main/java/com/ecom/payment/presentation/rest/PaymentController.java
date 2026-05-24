package com.ecom.payment.presentation.rest;

import com.ecom.payment.application.port.PaymentGateway;
import com.ecom.payment.domain.model.Payment;
import com.ecom.payment.domain.repository.PaymentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {
    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;

    public PaymentController(PaymentRepository paymentRepository, PaymentGateway paymentGateway) {
        this.paymentRepository = paymentRepository;
        this.paymentGateway = paymentGateway;
    }

    @PostMapping
    public ResponseEntity<Payment> processPayment(@RequestBody PaymentRequest request) {
        boolean success = paymentGateway.process(request.getAmount());
        String status = success ? "COMPLETED" : "FAILED";

        Payment payment = new Payment(
                UUID.randomUUID(),
                request.getOrderId(),
                request.getAmount(),
                status,
                LocalDateTime.now()
        );

        Payment saved = paymentRepository.save(payment);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        // We can just add a simple database list or helper if needed. Let's see if we should fetch from DB.
        // Wait, does paymentRepository support findAll? Let's check how paymentRepository is implemented.
        // Let's implement it directly using JpaPaymentRepository or query from paymentRepository if it supports it.
        // Wait, let's see if the paymentRepository has a findByOrderId or findById method.
        return ResponseEntity.ok(paymentRepository.findAll());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable UUID orderId) {
        return paymentRepository.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public static class PaymentRequest {
        private UUID orderId;
        private BigDecimal amount;

        public UUID getOrderId() { return orderId; }
        public void setOrderId(UUID orderId) { this.orderId = orderId; }
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
    }
}
