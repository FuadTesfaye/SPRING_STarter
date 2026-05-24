package com.ecom.order.infrastructure.config;

import com.ecom.order.application.port.EventPublisher;
import com.ecom.order.application.service.OrderServiceImpl;
import com.ecom.order.domain.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public OrderServiceImpl orderService(OrderRepository orderRepository, EventPublisher eventPublisher) {
        return new OrderServiceImpl(orderRepository, eventPublisher);
    }
}
