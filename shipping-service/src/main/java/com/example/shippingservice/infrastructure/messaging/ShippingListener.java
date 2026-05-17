package com.example.shippingservice.infrastructure.messaging;

import com.example.shippingservice.application.dto.PaymentCompletedEvent;
import com.example.shippingservice.application.dto.StockReservedEvent;
import com.example.shippingservice.application.service.ShippingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ShippingListener {

    private final ShippingService shippingService;

    public ShippingListener(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @RabbitListener(queues = RabbitMQConfig.SHIPPING_QUEUE)
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        System.out.println("📩 Shipping received → payment.completed for order: " + event.getOrderId());
        shippingService.processPaymentCompleted(event);
    }

    @RabbitListener(queues = RabbitMQConfig.SHIPPING_QUEUE)
    public void handleStockReserved(StockReservedEvent event) {
        System.out.println("📩 Shipping received → stock.reserved for order: " + event.getOrderId());
        shippingService.processStockReserved(event);
    }
}