package com.app.shipping.infrastructure.messaging;

import com.app.shipping.application.usecases.CreateShipmentUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ShippingEventListeners {

    private final CreateShipmentUseCase createShipmentUseCase;

    public ShippingEventListeners(CreateShipmentUseCase createShipmentUseCase) {
        this.createShipmentUseCase = createShipmentUseCase;
    }

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_COMPLETED_QUEUE)
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        createShipmentUseCase.handlePaymentCompleted(event.orderId());
    }

    @RabbitListener(queues = RabbitMQConfig.STOCK_RESERVED_QUEUE)
    public void onStockReserved(StockReservedEvent event) {
        createShipmentUseCase.handleStockReserved(event.orderId());
    }
}
