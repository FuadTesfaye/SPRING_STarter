package com.assignment.shipping.infrastructure.messaging;

import com.assignment.shipping.application.usecase.CreateShipmentUseCase;
import com.assignment.shipping.infrastructure.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ShippingEventListener {

    private static final Logger log = LoggerFactory.getLogger(ShippingEventListener.class);
    private final CreateShipmentUseCase createShipmentUseCase;

    public ShippingEventListener(CreateShipmentUseCase createShipmentUseCase) {
        this.createShipmentUseCase = createShipmentUseCase;
    }

    @RabbitListener(queues = RabbitMQConfig.SHIPPING_QUEUE)
    public void onEvent(Map<String, Object> event,
                        @Header(value = "amqp_receivedRoutingKey", required = false) String routingKey) {
        log.info("[SHIPPING] Received event: routingKey={}", routingKey);
        if ("payment.completed".equals(routingKey)) {
            createShipmentUseCase.onPaymentCompleted(event);
        } else if ("stock.reserved".equals(routingKey)) {
            createShipmentUseCase.onStockReserved(event);
        }
    }
}
