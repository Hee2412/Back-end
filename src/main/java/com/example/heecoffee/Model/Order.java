package com.example.heecoffee.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    public enum PaymentMethod {
        MOMO, VNPay, CARD, CASH
    }

    public enum OrderStatus {
        ACTIVE, PENDING_PAYMENT, COMPLETED, CANCELED, ABANDONED, SHIPPING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "guest_name")
    private String guest_name;

    @Column(name = "guest_address")
    private String guest_address;

    @Column(name = "guest_email")
    private String guest_email;

    @Column(name = "guest_phone")
    private String guest_phone;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;

    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (orderStatus == null) {
            orderStatus = OrderStatus.PENDING_PAYMENT;
        }
    }

    @Column(name = "is_paid")
    private Boolean isPaid = false;  // Đã thanh toán chưa

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    public boolean isGuestOrder() {
        return this.user == null;
    }

    public String getCustomerName() {
        return isGuestOrder() ? guest_name : user.getName();
    }

    public String getCustomerEmail() {
        return isGuestOrder() ? guest_email : user.getEmail();
    }

    public String getCustomerAddress() {
        return isGuestOrder() ? guest_address : user.getAddress();
    }
}