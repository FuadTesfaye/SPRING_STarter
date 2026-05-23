package com.school.notification.application.service;

import com.school.notification.domain.event.GenericEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public void handleUserRegistered(GenericEventDto event) {
        log.info("=== NOTIFICATION === [USER REGISTERED] Student: {} | Email: {}",
            event.getStudentName(), event.getStudentId());
    }

    public void handleOrderCreated(GenericEventDto event) {
        log.info("=== NOTIFICATION === [ORDER CREATED] OrderId: {} | Student: {} | Fee: {} | Amount: N/A",
            event.getOrderId(), event.getStudentId(), event.getFeeType());
    }

    public void handlePaymentCompleted(GenericEventDto event) {
        log.info("=== NOTIFICATION === [PAYMENT SUCCESS] OrderId: {} | Student: {} | Ref: {}",
            event.getOrderId(), event.getStudentId(), event.getReference());
    }

    public void handlePaymentFailed(GenericEventDto event) {
        log.warn("=== NOTIFICATION === [PAYMENT FAILED] OrderId: {} | Student: {} | Reason: {}",
            event.getOrderId(), event.getStudentId(), event.getReason());
    }

    public void handleStockReserved(GenericEventDto event) {
        log.info("=== NOTIFICATION === [STOCK RESERVED] OrderId: {} | FeeType: {}",
            event.getOrderId(), event.getFeeType());
    }

    public void handleStockFailed(GenericEventDto event) {
        log.warn("=== NOTIFICATION === [STOCK FAILED] OrderId: {} | Reason: {}",
            event.getOrderId(), event.getReason());
    }

    public void handleShipmentCreated(GenericEventDto event) {
        log.info("=== NOTIFICATION === [SHIPMENT CREATED] OrderId: {} | Student: {} | Tracking: {}",
            event.getOrderId(), event.getStudentId(), event.getTrackingNumber());
    }
}
