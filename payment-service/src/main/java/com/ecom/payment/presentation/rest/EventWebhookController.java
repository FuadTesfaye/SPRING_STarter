package com.ecom.payment.presentation.rest;

import com.ecom.payment.application.dto.OrderCreatedEvent;
import com.ecom.payment.application.service.PaymentProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventWebhookController {

    private final PaymentProcessor paymentProcessor;

    public EventWebhookController(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    @PostMapping("/order-created")
    public ResponseEntity<String> handleOrderCreated(@RequestBody OrderCreatedEvent event) {
        paymentProcessor.processPayment(event);
        return ResponseEntity.ok("PAYMENT_PROCESSED");
    }
}
