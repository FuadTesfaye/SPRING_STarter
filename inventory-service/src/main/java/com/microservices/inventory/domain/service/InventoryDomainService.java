package com.microservices.inventory.domain.service;

import com.microservices.inventory.domain.event.InventoryEventPublisher;
import com.microservices.inventory.domain.event.StockFailedEvent;
import com.microservices.inventory.domain.event.StockReservedEvent;
import com.microservices.inventory.domain.model.StockReservation;
import com.microservices.inventory.domain.repository.StockReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InventoryDomainService {
    
    private static final Logger log = LoggerFactory.getLogger(InventoryDomainService.class);
    
    private final StockReservationRepository repository;
    private final InventoryEventPublisher eventPublisher;
    
    public InventoryDomainService(StockReservationRepository repository,
                                   InventoryEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }
    
    /**
     * Check stock and reserve if available
     * MOCK: If quantity > 10, fail (out of stock)
     */
    public StockReservation checkAndReserve(String orderId, String productId,
                                             String productName, int quantity) {
        log.info("Checking stock for product: {}, quantity: {}", productId, quantity);
        
        StockReservation reservation;
        
        // MOCK INVENTORY CHECK
        if (quantity <= 10) {
            // Stock available - reserve it
            reservation = StockReservation.reserve(orderId, productId, productName, quantity);
            StockReservation saved = repository.save(reservation);
            
            eventPublisher.publishStockReserved(
                new StockReservedEvent(saved.getId(), saved.getOrderId(),
                    saved.getProductId(), saved.getProductName(), saved.getQuantity())
            );
            
            log.info("Stock reserved: {}", saved.getId());
        } else {
            // Out of stock
            reservation = StockReservation.fail(orderId, productId, productName, quantity,
                "Insufficient stock - requested " + quantity + ", max available: 10");
            StockReservation saved = repository.save(reservation);
            
            eventPublisher.publishStockFailed(
                new StockFailedEvent(saved.getId(), saved.getOrderId(),
                    saved.getProductId(), saved.getProductName(),
                    saved.getQuantity(), saved.getReason())
            );
            
            log.warn("Stock reservation failed: {}", saved.getId());
        }
        
        return reservation;
    }
    
    public StockReservation findById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found: " + id));
    }
    
    public List<StockReservation> findByOrderId(String orderId) {
        return repository.findByOrderId(orderId);
    }
    
    public List<StockReservation> findAll() {
        return repository.findAll();
    }
}
