package com.example.heecoffee.Service.Notification;

import com.example.heecoffee.Model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    // Gửi thông báo đơn mới cho Admin
    public void notifyNewOrder(Order order) {

        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "NEW_ORDER");
        payload.put("orderId", order.getId());
        payload.put("customerName", order.getGuest_name());
        payload.put("totalAmount", order.getTotalAmount());
        payload.put("createdAt", LocalDateTime.now());
        payload.put("message", "New order received!");

        messagingTemplate.convertAndSend("/topic/admin/orders", payload);
    }

    // Nếu bạn muốn gửi thông báo thay đổi status
    public void notifyOrderStatusUpdate(Order order) {

        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "STATUS_UPDATE");
        payload.put("orderId", order.getId());
        payload.put("customerName", order.getGuest_name());
        payload.put("status", order.getOrderStatus().name());
        payload.put("createdAt", LocalDateTime.now());
        payload.put("message", "Order status changed!");

        messagingTemplate.convertAndSend("/topic/admin/orders", payload);
    }
}
