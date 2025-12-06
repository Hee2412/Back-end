package com.example.heecoffee.Dto.Response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class OrderItemResponse {
    private Integer productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;

    public OrderItemResponse(Integer productId, String productName, Integer quantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}

