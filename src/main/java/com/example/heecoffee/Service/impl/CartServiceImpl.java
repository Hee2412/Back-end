package com.example.heecoffee.Service.impl;

import com.example.heecoffee.Exception.ErrorCodeConstant;
import com.example.heecoffee.Exception.NotFoundException;
import com.example.heecoffee.Model.*;
import com.example.heecoffee.Repository.CartRepository;
import com.example.heecoffee.Repository.ProductRepository;
import com.example.heecoffee.Repository.UserRepository;
import com.example.heecoffee.Service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    //Get ACTIVE user cart Api
    @Override
    public Cart getActiveCart(Integer userId) {
        if (userId == null) {
            Cart guestCart = new Cart();
            guestCart.setStatus(Cart.CartStatus.ACTIVE);
            return guestCart;
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found", ErrorCodeConstant.USER_NOT_FOUND));
        return cartRepository.findByUserIdAndStatus(userId, Cart.CartStatus.ACTIVE).orElseGet(() -> {
            Cart c = new Cart();
            c.setUser(user);
            c.setStatus(Cart.CartStatus.ACTIVE);
            return cartRepository.save(c);
        });
    }

    //Add item into cart
    @Transactional
    @Override
    public Cart addItemToCart(Integer userId, Integer productId, Integer quantity) {
        Cart cart = getActiveCart(userId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found", ErrorCodeConstant.PRODUCT_NOT_FOUND));

        CartItem item = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setUnitPrice(product.getPrice());
            cart.getCartItems().add(item);
        }
        updateCartTotals(cart);
        return cartRepository.save(cart);
    }

    private void updateCartTotals(Cart cart) {
        Integer totalItems = cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum();
        BigDecimal subTotal = cart.getCartItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalItems(totalItems);
        cart.setFinalAmount(subTotal);
    }

    @Transactional
    @Override
    public Cart removeItemFromCart(Integer userId, Integer productId, Integer quantity) {
        Cart cart = getActiveCart(userId);

        CartItem item = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Item not found in cart", ErrorCodeConstant.PRODUCT_NOT_FOUND));
        if (item.getQuantity() > quantity) {
            item.setQuantity(item.getQuantity() - quantity);
        } else {
            cart.getCartItems().remove(item);
        }
        updateCartTotals(cart);
        return cartRepository.save(cart);

    }
}
