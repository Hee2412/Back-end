package com.example.heecoffee.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cart_items")
public class CartItem {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    public enum Temperature {
        HOT, ICED
    }

    public enum IceLevel {
        NO_ICE, LESS_ICE, NORMAL, EXTRA_ICE
    }

    @Getter
    public enum SweetnessLevel {
        ZERO("0"), THIRTY("30%"), FIFTY("50%"),
        SEVENTY("70%"), HUNDRED("100%");


        private final String label;

        SweetnessLevel(String label) {
            this.label = label;
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "temperature")
    private Temperature temperature;

    @Enumerated(EnumType.STRING)
    @Column(name = "sweetness_level")
    private SweetnessLevel sweetnessLevel;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "ice_level")
    private IceLevel iceLevel;

    @Column(name = "special_notes")
    private String specialNotes;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //Total price of 1 item x quantity
    @Transient
    public BigDecimal getSubTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
