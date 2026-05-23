package com.assignment.inventory.infrastructure.messaging;

import com.assignment.inventory.application.usecase.ReserveStockUseCase;
import com.assignment.inventory.infrastructure.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);
    private final ReserveStockUseCase reserveStockUseCase;

    public OrderEventListener(ReserveStockUseCase reserveStockUseCase) {
        this.reserveStockUseCase = reserveStockUseCase;
    }

    @RabbitListener(queues = RabbitMQConfig.INVENTORY_QUEUE)
    public void onOrderCreated(Map<String, Object> event) {
        log.info("[INVENTORY] Received order.created event: {}", event);
        reserveStockUseCase.execute(event);
    }
}
