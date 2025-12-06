package com.example.heecoffee.Repository;

import com.example.heecoffee.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUserIdAndStatus(Integer userId, Cart.CartStatus status);
}
