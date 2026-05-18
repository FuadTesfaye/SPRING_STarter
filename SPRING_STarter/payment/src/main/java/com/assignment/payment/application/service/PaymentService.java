package com.assignment.payment.application.service;

import com.assignment.payment.application.dto.PaymentRequest;
import com.assignment.payment.application.dto.PaymentResponse;
import com.assignment.payment.domain.model.Payment;
import com.assignment.payment.domain.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final RestClient restClient;
    
    @Value("${services.notification}")
    private String notificationServiceUrl;
    
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
        this.restClient = RestClient.create();
    }
    
    public PaymentResponse processPayment(PaymentRequest request) {
        PaymentResponse response = new PaymentResponse();
        
        // Simulate payment processing (always success for demo)
        boolean success = true;
        
        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setEmail(request.getEmail());
        payment.setProcessedAt(LocalDateTime.now());
        
        if (success) {
            payment.setStatus("COMPLETED");
            payment.setTransactionId(UUID.randomUUID().toString());
            
            response.setSuccess(true);
            response.setMessage("Payment processed successfully");
            response.setTransactionId(payment.getTransactionId());
            
            System.out.println("✅ Payment completed for order: " + request.getOrderId());
        } else {
            payment.setStatus("FAILED");
            
            response.setSuccess(false);
            response.setMessage("Payment failed");
            
            System.out.println("❌ Payment failed for order: " + request.getOrderId());
        }
        
        paymentRepository.save(payment);
        
        // Send notification
        sendNotification(request.getOrderId(), "Payment " + payment.getStatus());
        
        return response;
    }
    
    private void sendNotification(Long orderId, String message) {
        try {
            Map<String, Object> notification = new HashMap<>();
            notification.put("service", "payment-service");
            notification.put("event", message);
            notification.put("orderId", orderId);
            notification.put("timestamp", System.currentTimeMillis());
            
            restClient.post()
                .uri(notificationServiceUrl + "/api/notifications/log")
                .body(notification)
                .retrieve();
            
        } catch (Exception e) {
            System.err.println("Notification failed (service may not be running): " + e.getMessage());
        }
    }
}