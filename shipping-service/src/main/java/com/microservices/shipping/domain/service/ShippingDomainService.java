package com.microservices.shipping.domain.service;

import com.microservices.shipping.domain.event.ShipmentCreatedEvent;
import com.microservices.shipping.domain.event.ShippingEventPublisher;
import com.microservices.shipping.domain.model.OrderTracker;
import com.microservices.shipping.domain.model.Shipment;
import com.microservices.shipping.domain.repository.OrderTrackerRepository;
import com.microservices.shipping.domain.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * SHIPPING DOMAIN SERVICE
 * 
 * SAGA PATTERN: Orchestrates shipping based on multiple events
 * Waits for BOTH payment.completed AND stock.reserved
 */
public class ShippingDomainService {
    
    private static final Logger log = LoggerFactory.getLogger(ShippingDomainService.class);
    
    private final OrderTrackerRepository trackerRepository;
    private final ShipmentRepository shipmentRepository;
    private final ShippingEventPublisher eventPublisher;
    
    public ShippingDomainService(OrderTrackerRepository trackerRepository,
                                  ShipmentRepository shipmentRepository,
                                  ShippingEventPublisher eventPublisher) {
        this.trackerRepository = trackerRepository;
        this.shipmentRepository = shipmentRepository;
        this.eventPublisher = eventPublisher;
    }
    
    /**
     * Handle payment completed event
     */
    public void handlePaymentCompleted(String orderId, String paymentId) {
        log.info("💰 Payment completed for order: {}", orderId);
        
        OrderTracker tracker = getOrCreateTracker(orderId);
        tracker.markPaymentCompleted(paymentId);
        trackerRepository.save(tracker);
        
        checkAndShip(tracker);
    }
    
    /**
     * Handle stock reserved event
     */
    public void handleStockReserved(String orderId, String reservationId) {
        log.info("📦 Stock reserved for order: {}", orderId);
        
        OrderTracker tracker = getOrCreateTracker(orderId);
        tracker.markStockReserved(reservationId);
        trackerRepository.save(tracker);
        
        checkAndShip(tracker);
    }
    
    /**
     * Check if both conditions are met and create shipment
     */
    private void checkAndShip(OrderTracker tracker) {
        if (tracker.isReadyToShip()) {
            log.info("========================================");
            log.info("🚚 BOTH CONDITIONS MET! Creating shipment...");
            log.info("   Order: {}", tracker.getOrderId());
            log.info("   Payment: {}", tracker.getPaymentId());
            log.info("   Reservation: {}", tracker.getReservationId());
            log.info("========================================");
            
            // Create shipment
            Shipment shipment = Shipment.create(
                tracker.getOrderId(),
                tracker.getPaymentId(),
                tracker.getReservationId()
            );
            shipmentRepository.save(shipment);
            
            // Publish event
            eventPublisher.publishShipmentCreated(
                new ShipmentCreatedEvent(
                    shipment.getId(),
                    shipment.getOrderId(),
                    shipment.getTrackingNumber()
                )
            );
            
            log.info("✅ Shipment created: {} | Tracking: {}", 
                     shipment.getId(), shipment.getTrackingNumber());
        } else {
            log.info("⏳ Waiting for remaining events... Payment: {}, Stock: {}",
                     tracker.isPaymentCompleted() ? "✅" : "⏳",
                     tracker.isStockReserved() ? "✅" : "⏳");
        }
    }
    
    private OrderTracker getOrCreateTracker(String orderId) {
        Optional<OrderTracker> existing = trackerRepository.findByOrderId(orderId);
        return existing.orElseGet(() -> {
            log.info("Creating new tracker for order: {}", orderId);
            return OrderTracker.create(orderId);
        });
    }
    
    public Shipment findById(String id) {
        return shipmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Shipment not found: " + id));
    }
    
    public List<Shipment> findAll() {
        return shipmentRepository.findAll();
    }
}
