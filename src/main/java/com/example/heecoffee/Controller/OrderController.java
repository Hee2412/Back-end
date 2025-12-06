package com.example.heecoffee.Controller;


import com.example.heecoffee.Dto.Request.StatusUpdateRequest;
import com.example.heecoffee.Dto.Response.ApiResponse;
import com.example.heecoffee.Dto.Request.CheckoutRequest;
import com.example.heecoffee.Dto.Response.OrderResponse;
import com.example.heecoffee.Model.Order;
import com.example.heecoffee.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
//ADMIN END-POINT
    //1. Change order status api
    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse> updateOrderStatus(@PathVariable Integer orderId,
                                                         @RequestBody StatusUpdateRequest request) {
        Order.OrderStatus newStatusEnum = Order.OrderStatus.valueOf(request.getStatus());
        OrderResponse order = orderService.updateOrderStatus(orderId, newStatusEnum);
        return ResponseEntity.ok(new ApiResponse("Success", order));
    }

    //2. Get order by status
    @GetMapping("/new-order")
    public ResponseEntity<ApiResponse> getNewOrder() {
        List<OrderResponse> orders = orderService.getOrderByStatus(Order.OrderStatus.ACTIVE);
        return ResponseEntity.ok(new ApiResponse("Success", orders));
    }

    //3. count new order
    @GetMapping("/new-orders-count")
    public ResponseEntity<ApiResponse> getNewOrdersCount() {
        Long count = orderService.getNewOrderCount();
        return ResponseEntity.ok(new ApiResponse("Success", count));
    }

    //4. Get all orders
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(new ApiResponse("Success", orders));
    }

//USER END POINT
    //1. Check out method
    @PostMapping
    public ResponseEntity<ApiResponse> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        OrderResponse order = orderService.checkout(checkoutRequest);
        return ResponseEntity.ok(new ApiResponse("Success", order));
    }

    //2. Generate QR
    @GetMapping("/qr/{id}")
    public ResponseEntity<ApiResponse> getOrderQr(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse("Success", orderService.generateQrCode(id)));
    }

    //3. Confirm Payment
    @PutMapping("/{orderId}/pay")
    public ResponseEntity<ApiResponse> confirmOrder(@PathVariable Integer orderId) {
        OrderResponse orderResponse = orderService.updateOrderStatus(orderId, Order.OrderStatus.ACTIVE);
        return ResponseEntity.ok(new ApiResponse("Success", orderResponse));
    }

    //4. Cancel Order
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Integer orderId) {
        OrderResponse orderResponse = orderService.updateOrderStatus(orderId, Order.OrderStatus.CANCELED);
        return ResponseEntity.ok(new ApiResponse("Success", orderResponse));
    }

    //5. Get order from user to see history
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getAllOrders(@PathVariable Integer userId) {
        List<OrderResponse> order = orderService.findByUserId(userId);
        return ResponseEntity.ok(new ApiResponse("Success", order));
    }

    //5. Get details of 1 order
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse> getOrderDetails(@PathVariable Integer orderId) {
        OrderResponse orderResponse = orderService.getOrderById(orderId);
        return ResponseEntity.ok(new ApiResponse("Success", orderResponse));
    }


}
