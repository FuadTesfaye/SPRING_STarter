package com.example.shipping.infrastructure.messaging;

import com.example.events.PaymentCompletedEvent;
import com.example.events.QueueNames;
import com.example.events.StockReservedEvent;
import com.example.shipping.application.service.ShippingApplicationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ShippingEventListener {

    private final ShippingApplicationService shippingApplicationService;

    public ShippingEventListener(ShippingApplicationService shippingApplicationService) {
        this.shippingApplicationService = shippingApplicationService;
    }

    @RabbitListener(queues = QueueNames.SHIPPING_EVENTS_QUEUE)
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        shippingApplicationService.handlePaymentCompleted(event);
    }

    @RabbitListener(queues = QueueNames.SHIPPING_EVENTS_QUEUE)
    public void handleStockReserved(StockReservedEvent event) {
        shippingApplicationService.handleStockReserved(event);
    }
}
