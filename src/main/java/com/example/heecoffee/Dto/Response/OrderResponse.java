package com.example.heecoffee.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Integer orderId;
    private String guestName;
    private String guestEmail;
    private String guestAddress;
    private String guestPhone;
    private BigDecimal totalAmount;

    private String paymentMethod;
    private String status;
    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;

    private Boolean isPaid;
    private LocalDateTime paidAt;
    private Long minutesSincePaid;


    public OrderResponse(Integer orderId, String guestName, String guestEmail, String guestAddress, String guestPhone, BigDecimal totalAmount, String paymentMethod, String status, LocalDateTime createdAt, List<OrderItemResponse> items) {
        this.orderId = orderId;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.guestAddress = guestAddress;
        this.guestPhone = guestPhone;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
        this.items = items;
    }

}
