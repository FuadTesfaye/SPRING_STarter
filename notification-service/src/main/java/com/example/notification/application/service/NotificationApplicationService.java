package com.example.notification.application.service;

import com.example.events.OrderCreatedEvent;
import com.example.events.PaymentCompletedEvent;
import com.example.events.PaymentFailedEvent;
import com.example.events.ShipmentCreatedEvent;
import com.example.events.StockFailedEvent;
import com.example.events.StockReservedEvent;
import com.example.events.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationApplicationService.class);

    public void notifyUserRegistered(UserRegisteredEvent event) {
        logger.info("📧 NOTIFICATION: User Registered - Username: {}, Email: {}, Timestamp: {}",
                event.username(), event.email(), event.occurredAt());
    }

    public void notifyOrderCreated(OrderCreatedEvent event) {
        logger.info("📦 NOTIFICATION: Order Created - OrderId: {}, UserId: {}, ProductId: {}, Quantity: {}, Timestamp: {}",
                event.orderId(), event.userId(), event.productId(), event.quantity(), event.occurredAt());
    }

    public void notifyPaymentCompleted(PaymentCompletedEvent event) {
        logger.info("✅ NOTIFICATION: Payment Completed - OrderId: {}, Reference: {}, Amount: {}, Timestamp: {}",
                event.orderId(), event.paymentReference(), event.amount(), event.occurredAt());
    }

    public void notifyPaymentFailed(PaymentFailedEvent event) {
        logger.warn("❌ NOTIFICATION: Payment Failed - OrderId: {}, Reason: {}, Timestamp: {}",
                event.orderId(), event.reason(), event.occurredAt());
    }

    public void notifyStockReserved(StockReservedEvent event) {
        logger.info("📊 NOTIFICATION: Stock Reserved - OrderId: {}, ProductId: {}, Quantity: {}, Timestamp: {}",
                event.orderId(), event.productId(), event.quantity(), event.occurredAt());
    }

    public void notifyStockFailed(StockFailedEvent event) {
        logger.warn("⚠️ NOTIFICATION: Stock Reservation Failed - OrderId: {}, Reason: {}, Timestamp: {}",
                event.orderId(), event.reason(), event.occurredAt());
    }

    public void notifyShipmentCreated(ShipmentCreatedEvent event) {
        logger.info("🚚 NOTIFICATION: Shipment Created - OrderId: {}, ShipmentId: {}, Status: {}, Timestamp: {}",
                event.orderId(), event.shipmentId(), event.shipmentStatus(), event.occurredAt());
    }
}
