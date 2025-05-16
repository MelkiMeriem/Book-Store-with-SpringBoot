package com.example.bookstore.service;

import com.example.bookstore.model.Order;
import com.example.bookstore.model.User;
import com.example.bookstore.model.CartItem;
import java.util.List;

public interface OrderService {
    List<Order> getOrdersByUser(User user);
    Order createOrder(User user, List<CartItem> cartItems);
    Order getOrderById(Long id);
    Order updateOrderStatus(Long orderId, Order.OrderStatus status);
} 