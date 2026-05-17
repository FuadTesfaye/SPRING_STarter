package com.microservices.shipping.infrastructure.messaging;

import com.microservices.shipping.domain.service.ShippingDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * SHIPPING EVENT LISTENER
 * 
 * WHAT: Listens to TWO events:
 * 1. payment.completed (from Payment Service)
 * 2. stock.reserved (from Inventory Service)
 * 
 * SAGA PATTERN: Only creates shipment when BOTH are received
 */
@Component
public class ShippingEventListener {
    
    private static final Logger log = LoggerFactory.getLogger(ShippingEventListener.class);
    private final ShippingDomainService shippingDomainService;
    
    public ShippingEventListener(ShippingDomainService shippingDomainService) {
        this.shippingDomainService = shippingDomainService;
    }
    
    /**
     * Listen for payment.completed events
     * 🟢 EVENT 1 of 2 needed
     */
    @RabbitListener(queues = "${app.rabbitmq.queue.payment-completed}")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("========================================");
        log.info("🚚 Shipping Service: Received payment.completed");
        log.info("   Payment ID: {}", event.getPaymentId());
        log.info("   Order ID: {}", event.getOrderId());
        log.info("   Status: 1/2 events received (waiting for stock)");
        log.info("========================================");
        
        shippingDomainService.handlePaymentCompleted(
            event.getOrderId(), 
            event.getPaymentId()
        );
    }
    
    /**
     * Listen for stock.reserved events
     * 🟢 EVENT 2 of 2 needed
     */
    @RabbitListener(queues = "${app.rabbitmq.queue.stock-reserved}")
    public void handleStockReserved(StockReservedEvent event) {
        log.info("========================================");
        log.info("🚚 Shipping Service: Received stock.reserved");
        log.info("   Reservation ID: {}", event.getReservationId());
        log.info("   Order ID: {}", event.getOrderId());
        log.info("   Product: {} (Qty: {})", event.getProductName(), event.getQuantity());
        log.info("   Status: 2/2 events received! 🎉");
        log.info("========================================");
        
        shippingDomainService.handleStockReserved(
            event.getOrderId(), 
            event.getReservationId()
        );
    }
}
