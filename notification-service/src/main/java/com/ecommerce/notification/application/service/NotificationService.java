package com.ecommerce.notification.application.service;

import com.ecommerce.notification.domain.event.*;
import com.ecommerce.notification.domain.model.Notification;
import com.ecommerce.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void onUserRegistered(UserRegisteredEvent e) {
        String msg = String.format("Welcome %s! Your account has been created with email: %s", e.getUsername(), e.getEmail());
        save("USER_REGISTERED", e.getUserId(), msg);
        log.info("[NOTIFICATION] USER_REGISTERED | userId={} email={}", e.getUserId(), e.getEmail());
    }

    public void onOrderCreated(OrderCreatedEvent e) {
        String msg = String.format("Order %s created for user %s | Product: %s x%d | Amount: $%s",
                e.getOrderId(), e.getUserId(), e.getProductId(), e.getQuantity(), e.getAmount());
        save("ORDER_CREATED", e.getOrderId(), msg);
        log.info("[NOTIFICATION] ORDER_CREATED | orderId={} userId={}", e.getOrderId(), e.getUserId());
    }

    public void onPaymentCompleted(PaymentCompletedEvent e) {
        String msg = String.format("Payment successful for order %s | Amount: $%s | PaymentId: %s",
                e.getOrderId(), e.getAmount(), e.getPaymentId());
        save("PAYMENT_COMPLETED", e.getOrderId(), msg);
        log.info("[NOTIFICATION] PAYMENT_COMPLETED | orderId={} paymentId={}", e.getOrderId(), e.getPaymentId());
    }

    public void onPaymentFailed(PaymentFailedEvent e) {
        String msg = String.format("Payment FAILED for order %s | Reason: %s", e.getOrderId(), e.getReason());
        save("PAYMENT_FAILED", e.getOrderId(), msg);
        log.warn("[NOTIFICATION] PAYMENT_FAILED | orderId={} reason={}", e.getOrderId(), e.getReason());
    }

    public void onStockReserved(StockReservedEvent e) {
        String msg = String.format("Stock reserved for order %s | Product: %s x%d",
                e.getOrderId(), e.getProductId(), e.getQuantity());
        save("STOCK_RESERVED", e.getOrderId(), msg);
        log.info("[NOTIFICATION] STOCK_RESERVED | orderId={} productId={}", e.getOrderId(), e.getProductId());
    }

    public void onStockFailed(StockFailedEvent e) {
        String msg = String.format("Stock check FAILED for order %s | Reason: %s", e.getOrderId(), e.getReason());
        save("STOCK_FAILED", e.getOrderId(), msg);
        log.warn("[NOTIFICATION] STOCK_FAILED | orderId={} reason={}", e.getOrderId(), e.getReason());
    }

    public void onShipmentCreated(ShipmentCreatedEvent e) {
        String msg = String.format("Shipment created for order %s | Tracking: %s | To: %s",
                e.getOrderId(), e.getTrackingNumber(), e.getShippingAddress());
        save("SHIPMENT_CREATED", e.getOrderId(), msg);
        log.info("[NOTIFICATION] SHIPMENT_CREATED | orderId={} tracking={}", e.getOrderId(), e.getTrackingNumber());
    }

    private void save(String type, String refId, String msg) {
        notificationRepository.save(Notification.builder()
                .eventType(type).referenceId(refId).message(msg)
                .receivedAt(LocalDateTime.now()).build());
    }

    public List<Notification> findAll() { return notificationRepository.findAll(); }
    public List<Notification> findByType(String type) { return notificationRepository.findByEventType(type); }
}
