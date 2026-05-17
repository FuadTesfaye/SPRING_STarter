package com.microservices.payment.domain.service;

import com.microservices.payment.domain.event.PaymentCompletedEvent;
import com.microservices.payment.domain.event.PaymentEventPublisher;
import com.microservices.payment.domain.event.PaymentFailedEvent;
import com.microservices.payment.domain.model.Payment;
import com.microservices.payment.domain.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;  // ← MISSING IMPORT!

public class PaymentDomainService {
    
    private static final Logger log = LoggerFactory.getLogger(PaymentDomainService.class);
    
    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;
    
    public PaymentDomainService(PaymentRepository paymentRepository, 
                                 PaymentEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }
    
    /**
     * Process payment for an order
     * Mock: Always succeeds for amounts < 1000, fails for higher amounts
     */
    public Payment processPayment(String orderId, BigDecimal amount) {
        log.info("Processing payment for order: {}, amount: {}", orderId, amount);
        
        Payment payment;
        
        // MOCK PAYMENT LOGIC
        if (amount.compareTo(new BigDecimal("1000")) < 0) {
            // Payment successful
            payment = Payment.processSuccessfully(orderId, amount);
            Payment saved = paymentRepository.save(payment);
            
            eventPublisher.publishPaymentCompleted(
                new PaymentCompletedEvent(saved.getId(), saved.getOrderId(), saved.getAmount())
            );
            
            log.info("Payment completed: {}", saved.getId());
            
        } else {
            // Payment failed - amount too high
            payment = Payment.processFailed(orderId, amount, "Amount exceeds limit");
            Payment saved = paymentRepository.save(payment);
            
            eventPublisher.publishPaymentFailed(
                new PaymentFailedEvent(saved.getId(), saved.getOrderId(), 
                                      saved.getAmount(), saved.getReason())
            );
            
            log.warn("Payment failed for order {}: amount too high", orderId);
        }
        
        return payment;
    }
    
    public Payment findById(String paymentId) {
        return paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found: " + paymentId));
    }
    
    public List<Payment> findByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
    
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }
}
