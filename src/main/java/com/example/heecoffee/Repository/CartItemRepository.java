package com.example.heecoffee.Repository;

import com.example.heecoffee.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
