package com.app.inventory.infrastructure.messaging;

import com.app.inventory.application.usecases.ReserveStockUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final ReserveStockUseCase reserveStockUseCase;

    public OrderCreatedListener(ReserveStockUseCase reserveStockUseCase) {
        this.reserveStockUseCase = reserveStockUseCase;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void onOrderCreated(OrderCreatedEvent event) {
        reserveStockUseCase.execute(event.orderId());
    }
}
