package com.example.heecoffee.Service.Notification;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderNotification {
    private Integer orderId;
    private String customerName;
    private BigDecimal totalAmount;
    private String status;
    private Boolean isPaid;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
    private String message;
    private String type; // NEW_ORDER, STATUS_UPDATE, PAYMENT_RECEIVED
}
