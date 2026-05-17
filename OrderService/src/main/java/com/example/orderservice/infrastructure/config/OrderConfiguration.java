package com.example.orderservice.infrastructure.config;

import com.example.orderservice.application.interfaces.InventoryGateway;
import com.example.orderservice.application.interfaces.NotificationGateway;
import com.example.orderservice.application.interfaces.PaymentGateway;
import com.example.orderservice.application.interfaces.ShipmentGateway;
import com.example.orderservice.application.usecases.PlaceOrderService;
import com.example.orderservice.application.usecases.PlaceOrderUseCase;
import com.example.orderservice.domain.interfaces.OrderRepository;
import com.example.orderservice.domain.services.OrderPricingService;
import com.example.orderservice.infrastructure.externalservices.InventoryHttpGateway;
import com.example.orderservice.infrastructure.externalservices.NotificationHttpGateway;
import com.example.orderservice.infrastructure.externalservices.PaymentHttpGateway;
import com.example.orderservice.infrastructure.externalservices.ShipmentHttpGateway;
import com.example.orderservice.infrastructure.persistence.adapter.OrderPersistenceAdapter;
import com.example.orderservice.infrastructure.persistence.adapter.OrderPersistenceMapper;
import com.example.orderservice.infrastructure.persistence.repository.SpringDataOrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OrderConfiguration {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public OrderPricingService orderPricingService() {
        return new OrderPricingService();
    }

    @Bean
    public OrderPersistenceMapper orderPersistenceMapper() {
        return new OrderPersistenceMapper();
    }

    @Bean
    public OrderRepository orderRepository(
            SpringDataOrderRepository springDataOrderRepository,
            OrderPersistenceMapper orderPersistenceMapper
    ) {
        return new OrderPersistenceAdapter(springDataOrderRepository, orderPersistenceMapper);
    }

    @Bean
    public InventoryGateway inventoryGateway(WebClient.Builder webClientBuilder, org.springframework.core.env.Environment environment) {
        return new InventoryHttpGateway(webClientBuilder, environment.getProperty("services.inventory.base-url"));
    }

    @Bean
    public PaymentGateway paymentGateway(WebClient.Builder webClientBuilder, org.springframework.core.env.Environment environment) {
        return new PaymentHttpGateway(webClientBuilder, environment.getProperty("services.payment.base-url"));
    }

    @Bean
    public ShipmentGateway shipmentGateway(WebClient.Builder webClientBuilder, org.springframework.core.env.Environment environment) {
        return new ShipmentHttpGateway(webClientBuilder, environment.getProperty("services.shipping.base-url"));
    }

    @Bean
    public NotificationGateway notificationGateway(WebClient.Builder webClientBuilder, org.springframework.core.env.Environment environment) {
        return new NotificationHttpGateway(webClientBuilder, environment.getProperty("services.notification.base-url"));
    }

    @Bean
    public PlaceOrderService placeOrderUseCase(
            OrderRepository orderRepository,
            OrderPricingService orderPricingService,
            InventoryGateway inventoryGateway,
            PaymentGateway paymentGateway,
            ShipmentGateway shipmentGateway,
            NotificationGateway notificationGateway
    ) {
        return new PlaceOrderUseCase(
                orderRepository,
                orderPricingService,
                inventoryGateway,
                paymentGateway,
                shipmentGateway,
                notificationGateway
        );
    }
}
