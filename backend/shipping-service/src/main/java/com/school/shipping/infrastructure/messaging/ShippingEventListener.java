package com.school.shipping.infrastructure.messaging;

import com.school.shipping.application.service.ShippingService;
import com.school.shipping.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShippingEventListener {

    private final ShippingService shippingService;

    @RabbitListener(queues = RabbitMQConfig.SHIPPING_PAYMENT_QUEUE)
    public void handlePaymentCompleted(PaymentCompletedEventDto event) {
        log.info("[SHIPPING] Received payment.completed for order: {}", event.getOrderId());
        shippingService.createShipment(event.getOrderId(), event.getStudentId());
    }

    @RabbitListener(queues = RabbitMQConfig.SHIPPING_STOCK_QUEUE)
    public void handleStockReserved(StockReservedEventDto event) {
        log.info("[SHIPPING] Received stock.reserved for order: {}", event.getOrderId());
        // In a real saga, wait for both payment + stock before shipping
        // Here we log as confirmation received
        log.info("[SHIPPING] Stock confirmed for order: {}", event.getOrderId());
    }
}
