package com.app.order.infrastructure.config;

import com.app.order.application.ports.EventPublisher;
import com.app.order.application.ports.OrderService;
import com.app.order.application.usecases.CreateOrderUseCase;
import com.app.order.domain.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public OrderService orderService(OrderRepository orderRepository, EventPublisher eventPublisher) {
        return new CreateOrderUseCase(orderRepository, eventPublisher);
    }
}
