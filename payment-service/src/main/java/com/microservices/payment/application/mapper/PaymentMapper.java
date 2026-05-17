package com.microservices.payment.application.mapper;

import com.microservices.payment.application.dto.PaymentResponse;
import com.microservices.payment.domain.model.Payment;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MAPPER: PaymentMapper
 * 
 * WHAT: Converts between Domain Payment and PaymentResponse DTO
 * WHY: Clean separation between domain objects and API responses
 */
public final class PaymentMapper {
    
    private PaymentMapper() {
        // Utility class
    }
    
    /**
     * Domain Payment → PaymentResponse DTO
     */
    public static PaymentResponse toResponse(Payment payment) {
        if (payment == null) return null;
        
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(payment.getId());
        response.setOrderId(payment.getOrderId());
        response.setAmount(payment.getAmount());
        response.setStatus(payment.getStatus().name());
        response.setReason(payment.getReason());
        response.setProcessedAt(payment.getProcessedAt());
        
        return response;
    }
    
    /**
     * List of Payments → List of PaymentResponse DTOs
     */
    public static List<PaymentResponse> toResponseList(List<Payment> payments) {
        return payments.stream()
            .map(PaymentMapper::toResponse)
            .collect(Collectors.toList());
    }
}
