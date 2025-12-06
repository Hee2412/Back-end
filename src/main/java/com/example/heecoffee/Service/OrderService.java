package com.example.heecoffee.Service;

import com.example.heecoffee.Dto.Request.CheckoutRequest;
import com.example.heecoffee.Dto.Response.OrderResponse;
import com.example.heecoffee.Model.Order;
import com.example.heecoffee.Model.User;

import java.util.List;

public interface OrderService {
    OrderResponse checkout(CheckoutRequest dto);
    List<OrderResponse> findByUserId(Integer userId);
    OrderResponse getOrderById(Integer orderId);
    OrderResponse updateOrderStatus(Integer orderId, Order.OrderStatus orderStatus);
    String generateQrCode(Integer orderId);
    List<OrderResponse> getAllOrders();
    List<OrderResponse> getOrderByStatus(Order.OrderStatus orderStatus);
    List<OrderResponse> getNewOrder();
    Long getNewOrderCount();

    void syncGuestOrderToUser(String guestEmail, User newUser);
}
