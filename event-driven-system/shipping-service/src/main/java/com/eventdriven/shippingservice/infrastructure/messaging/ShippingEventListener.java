package com.eventdriven.shippingservice.infrastructure.messaging;

import com.eventdriven.shippingservice.application.dto.PaymentCompletedEvent;
import com.eventdriven.shippingservice.application.dto.StockReservedEvent;
import com.eventdriven.shippingservice.application.service.ShippingApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ShippingEventListener {
    
    private static final Logger log = LoggerFactory.getLogger(ShippingEventListener.class);
    private final ShippingApplicationService shippingApplicationService;
    
    public ShippingEventListener(ShippingApplicationService shippingApplicationService) {
        this.shippingApplicationService = shippingApplicationService;
    }
    
    @RabbitListener(queues = "shipping.payment.completed.queue")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("Received PaymentCompleted event for order: {}", event.getOrderId());
        shippingApplicationService.handlePaymentCompleted(event.getOrderId());
    }
    
    @RabbitListener(queues = "shipping.stock.reserved.queue")
    public void handleStockReserved(StockReservedEvent event) {
        log.info("Received StockReserved event for order: {}", event.getOrderId());
        shippingApplicationService.handleStockReserved(event.getOrderId());
    }
}