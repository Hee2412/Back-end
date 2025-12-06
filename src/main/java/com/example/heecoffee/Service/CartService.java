package com.example.heecoffee.Service;

import com.example.heecoffee.Model.Cart;

public interface CartService {
    Cart getActiveCart(Integer userId);
    Cart addItemToCart(Integer userId, Integer productId, Integer quantity);
    Cart removeItemFromCart(Integer userId, Integer productId, Integer quantity);
}
