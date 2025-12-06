package com.example.heecoffee.Service.impl;

import com.example.heecoffee.Model.Order;
import com.example.heecoffee.Repository.OrderRepository;
import com.example.heecoffee.Service.OrderSchedulerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderSchedulerServiceImpl implements OrderSchedulerService {

    private final OrderRepository orderRepository;

    public OrderSchedulerServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Scheduled(fixedRate = 3600000)
    public void markAbandoned() {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);
        List<Order> pendingOrder = orderRepository.findByOrderStatusAndCreatedAtBefore(Order.OrderStatus.PENDING_PAYMENT, cutoff);

        for (Order order : pendingOrder) {
            order.setOrderStatus(Order.OrderStatus.ABANDONED);
            orderRepository.save(order);
        }
    }
}
