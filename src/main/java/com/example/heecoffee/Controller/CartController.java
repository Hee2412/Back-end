package com.example.heecoffee.Controller;

import com.example.heecoffee.Dto.Response.ApiResponse;
import com.example.heecoffee.Model.Cart;
import com.example.heecoffee.Service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
public class CartController {
    public final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    //Active cart
    @GetMapping
    public ResponseEntity<ApiResponse> getActiveCart(@RequestParam(required = false) Integer userId) {
        Cart cart = cartService.getActiveCart(userId);
        return ResponseEntity.ok(new ApiResponse("Success", cart));
    }

    //Add item
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Integer userId,
                                                     @RequestParam Integer productId,
                                                     @RequestParam(defaultValue = "1") Integer quantity) {
        Cart updateCart = cartService.addItemToCart(userId, productId, quantity);
        return ResponseEntity.ok(new ApiResponse("Success", updateCart));
    }

    //Delete item from cart
    @DeleteMapping("/{userId}/item/{productId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Integer userId,
                                                          @PathVariable Integer productId,
                                                          @RequestParam(defaultValue = "1") Integer quantity) {
        Cart updateCart = cartService.removeItemFromCart(userId, productId, quantity);
        return ResponseEntity.ok(new ApiResponse("Success", updateCart));
    }
}
