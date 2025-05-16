package com.example.bookstore.repository;

import com.example.bookstore.model.Order;
import com.example.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderDateDesc(User user);
} 