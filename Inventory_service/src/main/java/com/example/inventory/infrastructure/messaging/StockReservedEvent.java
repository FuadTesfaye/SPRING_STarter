package com.example.inventory.infrastructure.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockReservedEvent implements Serializable {
    private String orderId;
    private String productId;
    private int quantity;
}
