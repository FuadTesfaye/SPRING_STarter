package com.example.orderservice.application.usecases;

import com.example.orderservice.application.dto.request.InventoryReserveRequest;
import com.example.orderservice.application.dto.request.OrderCreateRequest;
import com.example.orderservice.application.dto.request.PaymentProcessRequest;
import com.example.orderservice.application.dto.request.SendNotificationRequest;
import com.example.orderservice.application.dto.request.ShipmentCreateRequest;
import com.example.orderservice.application.dto.response.InventoryReserveResponse;
import com.example.orderservice.application.dto.response.NotificationResponse;
import com.example.orderservice.application.dto.response.OrderResponse;
import com.example.orderservice.application.dto.response.PaymentProcessResponse;
import com.example.orderservice.application.dto.response.ShipmentResponse;
import com.example.orderservice.application.interfaces.InventoryGateway;
import com.example.orderservice.application.interfaces.NotificationGateway;
import com.example.orderservice.application.interfaces.PaymentGateway;
import com.example.orderservice.application.interfaces.ShipmentGateway;
import com.example.orderservice.application.usecases.PlaceOrderService;
import com.example.orderservice.domain.entities.Order;
import com.example.orderservice.domain.enums.OrderStatus;
import com.example.orderservice.domain.interfaces.OrderRepository;
import com.example.orderservice.domain.services.OrderPricingService;
import java.util.UUID;

public class PlaceOrderUseCase implements PlaceOrderService {

    private final OrderRepository orderRepository;
    private final OrderPricingService orderPricingService;
    private final InventoryGateway inventoryGateway;
    private final PaymentGateway paymentGateway;
    private final ShipmentGateway shipmentGateway;
    private final NotificationGateway notificationGateway;

    public PlaceOrderUseCase(
            OrderRepository orderRepository,
            OrderPricingService orderPricingService,
            InventoryGateway inventoryGateway,
            PaymentGateway paymentGateway,
            ShipmentGateway shipmentGateway,
            NotificationGateway notificationGateway
    ) {
        this.orderRepository = orderRepository;
        this.orderPricingService = orderPricingService;
        this.inventoryGateway = inventoryGateway;
        this.paymentGateway = paymentGateway;
        this.shipmentGateway = shipmentGateway;
        this.notificationGateway = notificationGateway;
    }

    public OrderResponse execute(OrderCreateRequest request) {
        validate(request);

        Order order = orderRepository.save(new Order(
                UUID.randomUUID().toString(),
                request.productId(),
                request.quantity(),
                orderPricingService.calculateAmount(request.productId(), request.quantity()),
                OrderStatus.CREATED,
                null,
                null,
                null,
                null,
                "NOT_SENT",
                "Order created"
        ));

        InventoryReserveResponse inventoryResponse = inventoryGateway.reserveInventory(
                new InventoryReserveRequest(order.productId(), order.quantity())
        );
        if (!"INVENTORY_UPDATED".equalsIgnoreCase(inventoryResponse.status())) {
            return toResponse(orderRepository.save(order.withOutcome(
                    OrderStatus.ORDER_REJECTED,
                    inventoryResponse.status(),
                    null,
                    null,
                    null,
                    "NOT_SENT",
                    inventoryResponse.message()
            )));
        }

        PaymentProcessResponse paymentResponse = paymentGateway.processPayment(
                new PaymentProcessRequest(order.orderId(), order.productId(), order.quantity(), order.amount())
        );
        if (!"PAYMENT_CONFIRMED".equalsIgnoreCase(paymentResponse.status())) {
            return toResponse(orderRepository.save(order.withOutcome(
                    OrderStatus.ORDER_REJECTED,
                    inventoryResponse.status(),
                    paymentResponse.status(),
                    null,
                    null,
                    "NOT_SENT",
                    paymentResponse.message()
            )));
        }

        ShipmentResponse shipmentResponse = shipmentGateway.createShipment(
                new ShipmentCreateRequest(order.orderId(), order.productId(), order.quantity())
        );
        NotificationResponse notificationResponse = notificationGateway.sendNotification(
                new SendNotificationRequest(
                        order.orderId(),
                        order.productId(),
                        "Order " + order.orderId() + " has been created successfully"
                )
        );

        OrderStatus finalStatus = "SHIPMENT_CREATED".equalsIgnoreCase(shipmentResponse.status())
                ? OrderStatus.ORDER_CONFIRMED
                : OrderStatus.ORDER_PENDING;

        return toResponse(orderRepository.save(order.withOutcome(
                finalStatus,
                inventoryResponse.status(),
                paymentResponse.status(),
                shipmentResponse.shipmentId(),
                shipmentResponse.status(),
                notificationResponse.status(),
                notificationResponse.message()
        )));
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.orderId(),
                order.productId(),
                order.quantity(),
                order.status().name(),
                order.inventoryStatus(),
                order.paymentStatus(),
                order.shipmentId(),
                order.shipmentStatus(),
                order.notificationStatus(),
                order.message()
        );
    }

    private void validate(OrderCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request body is required");
        }
        if (request.productId() == null) {
            throw new IllegalArgumentException("productId is required");
        }
        if (request.quantity() == null || request.quantity() <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
    }
}
