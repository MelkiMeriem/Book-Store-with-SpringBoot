package com.example.bookstore.service.impl;

import com.example.bookstore.model.*;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    @Override
    @Transactional
    public Order createOrder(User user, List<CartItem> cartItems) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setTotalAmount(cartItems.stream()
            .map(item -> item.getBook().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add));

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
} 