package com.example.heecoffee.Repository;

import com.example.heecoffee.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);

    List<Order> findByOrderStatusAndCreatedAtBefore(Order.OrderStatus orderStatus, LocalDateTime cutOff);

    List<Order> findByOrderStatus(Order.OrderStatus orderStatus);

    // Lấy đơn mới (ACTIVE + thanh toán sau thời điểm X)
    @Query("SELECT o FROM Order o WHERE o.orderStatus = 'ACTIVE' AND o.isPaid = true AND o.paidAt > :since ORDER BY o.paidAt DESC")
    List<Order> findNewOrders(@Param("since") LocalDateTime since);

    // Đếm đơn mới
    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderStatus = 'ACTIVE' AND o.isPaid = true AND o.paidAt > :since")
    Long countNewOrders(@Param("since") LocalDateTime since);

    @Query("SELECT o FROM Order o WHERE o.guest_email = :email AND o.user IS NULL")
    List<Order> findGuestOrdersByEmail(@Param("email") String guestEmail);
}
