package com.ecommerce.shipping.infrastructure.messaging;

import com.ecommerce.shared.messaging.event.PaymentCompletedEvent;
import com.ecommerce.shared.messaging.event.StockReservedEvent;
import com.ecommerce.shipping.application.usecase.ProcessShipmentUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ShippingEventListener {

    private final ProcessShipmentUseCase processShipmentUseCase;
    private final Map<String, Boolean> paymentStatus = new ConcurrentHashMap<>();
    private final Map<String, Boolean> stockStatus = new ConcurrentHashMap<>();

    public ShippingEventListener(ProcessShipmentUseCase processShipmentUseCase) {
        this.processShipmentUseCase = processShipmentUseCase;
    }

    @RabbitListener(queues = "shipping.queue")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("SHIPPING: Payment confirmed for order {}", event.getOrderId());
        paymentStatus.put(event.getOrderId(), true);
        checkAndShip(event.getOrderId());
    }

    @RabbitListener(queues = "shipping.queue")
    public void handleStockReserved(StockReservedEvent event) {
        log.info("SHIPPING: Stock reserved for order {}", event.getOrderId());
        stockStatus.put(event.getOrderId(), true);
        checkAndShip(event.getOrderId());
    }

    private void checkAndShip(String orderId) {
        if (Boolean.TRUE.equals(paymentStatus.get(orderId)) && Boolean.TRUE.equals(stockStatus.get(orderId))) {
            log.info("SHIPPING: Both Payment and Stock ready! Creating shipment for order {}", orderId);
            
            // Delegate to Application Layer UseCase
            processShipmentUseCase.initiate(orderId);
            
            // Clean up state
            paymentStatus.remove(orderId);
            stockStatus.remove(orderId);
        }
    }
}
