package com.company.order.infrastructure.persistence.entity;

import com.company.order.domain.model.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerId;
    private String productId;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private String status;
    private LocalDateTime createdAt;

    public static OrderEntity fromDomain(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setCustomerId(order.getCustomerId());
        entity.setProductId(order.getProductId());
        entity.setQuantity(order.getQuantity());
        entity.setPricePerUnit(order.getPricePerUnit());
        entity.setStatus(order.getStatus());
        entity.setCreatedAt(order.getCreatedAt());
        return entity;
    }

    public Order toDomain() {
        return new Order(
            this.id,
            this.customerId,
            this.productId,
            this.quantity,
            this.pricePerUnit,
            this.status,
            this.createdAt
        );
    }
}
