package com.microservices.notification.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NOTIFICATION SERVICE
 * 
 * WHAT: Logs all system events as notifications
 * WHY: Demonstrates event-driven communication
 * In production, this would send emails, SMS, push notifications, etc.
 */
public class NotificationService {
    
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    
    public void notifyUserRegistered(String userId, String email, String username) {
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log.info("📧 NOTIFICATION: New User Registered!");
        log.info("   User ID: {}", userId);
        log.info("   Email: {}", email);
        log.info("   Username: {}", username);
        log.info("   Action: Send welcome email to {}", email);
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
    
    public void notifyOrderCreated(String orderId, String userId) {
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log.info("📋 NOTIFICATION: New Order Created!");
        log.info("   Order ID: {}", orderId);
        log.info("   User ID: {}", userId);
        log.info("   Action: Send order confirmation");
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
    
    public void notifyPaymentCompleted(String paymentId, String orderId) {
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log.info("💰 NOTIFICATION: Payment Completed!");
        log.info("   Payment ID: {}", paymentId);
        log.info("   Order ID: {}", orderId);
        log.info("   Action: Send payment receipt");
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
    
    public void notifyPaymentFailed(String paymentId, String orderId, String reason) {
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log.info("❌ NOTIFICATION: Payment Failed!");
        log.info("   Payment ID: {}", paymentId);
        log.info("   Order ID: {}", orderId);
        log.info("   Reason: {}", reason);
        log.info("   Action: Send payment failure notice");
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
    
    public void notifyStockReserved(String reservationId, String orderId, String productName) {
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log.info("📦 NOTIFICATION: Stock Reserved!");
        log.info("   Reservation ID: {}", reservationId);
        log.info("   Order ID: {}", orderId);
        log.info("   Product: {}", productName);
        log.info("   Action: Update inventory status");
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
    
    public void notifyStockFailed(String reservationId, String orderId, String reason) {
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log.info("⚠️ NOTIFICATION: Stock Reservation Failed!");
        log.info("   Reservation ID: {}", reservationId);
        log.info("   Order ID: {}", orderId);
        log.info("   Reason: {}", reason);
        log.info("   Action: Alert warehouse team");
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
    
    public void notifyShipmentCreated(String shipmentId, String orderId, String trackingNumber) {
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log.info("🚚 NOTIFICATION: Shipment Created!");
        log.info("   Shipment ID: {}", shipmentId);
        log.info("   Order ID: {}", orderId);
        log.info("   Tracking: {}", trackingNumber);
        log.info("   Action: Send shipping confirmation with tracking number");
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
