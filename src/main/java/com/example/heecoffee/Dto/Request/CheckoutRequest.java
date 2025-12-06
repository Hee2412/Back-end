package com.example.heecoffee.Dto.Request;

import com.example.heecoffee.Model.Order;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CheckoutRequest {
    private Integer userId;
    private String guestName;
    private String guestAddress;
    private String guestEmail;
    private String guestPhone;  // ✨ THÊM phone

    private Order.PaymentMethod paymentMethod;

    private List<CheckoutItemRequest> items;

    @Getter
    @Setter
    public static class CheckoutItemRequest {
        private Integer productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
    }
}