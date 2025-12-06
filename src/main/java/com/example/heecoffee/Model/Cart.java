package com.example.heecoffee.Model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    public enum CartStatus {
        ACTIVE, PENDING_PAYMENT, COMPLETED, CANCELED, ABANDONED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CartStatus status = CartStatus.ACTIVE;

    @Column(name = "total_items")
    private Integer totalItems;

    @Column(name = "discount_amount", precision = 12, scale = 2)
    private BigDecimal discount;

    @Column(name = "tax_amount", precision = 12, scale = 2)
    private BigDecimal tax;

    @Column(name = "final_amount", precision = 12, scale = 2)
    private BigDecimal finalAmount;

    @Column(name = "coupon_code")
    private String couponCode;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "checkout_at")
    private LocalDateTime checkoutAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Transient
    public BigDecimal getTotalAmount() {
        return cartItems.stream()
                .map(CartItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
