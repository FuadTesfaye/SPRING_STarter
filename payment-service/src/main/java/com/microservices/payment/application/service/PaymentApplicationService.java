package com.microservices.payment.application.service;

import com.microservices.payment.application.dto.PaymentResponse;
import com.microservices.payment.application.mapper.PaymentMapper;
import com.microservices.payment.domain.model.Payment;
import com.microservices.payment.domain.service.PaymentDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * APPLICATION SERVICE: PaymentApplicationService
 * 
 * WHAT: Orchestrates payment use cases
 * WHY: 
 * - Translates between DTOs and domain objects
 * - Provides clean API for presentation layer
 * - Handles application-level concerns (logging)
 * 
 * USE CASES:
 * 1. Process Payment (called by event listener)
 * 2. Query Payments
 */
public class PaymentApplicationService {
    
    private static final Logger log = LoggerFactory.getLogger(PaymentApplicationService.class);
    
    private final PaymentDomainService paymentDomainService;
    
    public PaymentApplicationService(PaymentDomainService paymentDomainService) {
        this.paymentDomainService = paymentDomainService;
    }
    
    /**
     * Process payment (called from event listener)
     * Returns DTO instead of domain object
     */
    public PaymentResponse processPayment(String orderId, BigDecimal amount) {
        log.info("Application: Processing payment for order: {}", orderId);
        
        Payment payment = paymentDomainService.processPayment(orderId, amount);
        
        return PaymentMapper.toResponse(payment);
    }
    
    /**
     * Get payment by ID
     */
    public PaymentResponse getPayment(String paymentId) {
        log.info("Application: Fetching payment: {}", paymentId);
        
        Payment payment = paymentDomainService.findById(paymentId);
        return PaymentMapper.toResponse(payment);
    }
    
    /**
     * Get payments by order ID
     */
    public List<PaymentResponse> getPaymentsByOrder(String orderId) {
        log.info("Application: Fetching payments for order: {}", orderId);
        
        List<Payment> payments = paymentDomainService.findByOrderId(orderId);
        return PaymentMapper.toResponseList(payments);
    }
    
    /**
     * Get all payments
     */
    public List<PaymentResponse> getAllPayments() {
        log.info("Application: Fetching all payments");
        
        List<Payment> payments = paymentDomainService.findAll();
        return PaymentMapper.toResponseList(payments);
    }
}
