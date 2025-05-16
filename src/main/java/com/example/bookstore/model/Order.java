package com.example.bookstore.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private LocalDateTime orderDate;
    
    @Column(nullable = false)
    private BigDecimal totalAmount;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    public enum OrderStatus {
        PENDING, PROCESSING, COMPLETED, CANCELLED
    }
    
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }
    
    public void removeOrderItem(OrderItem item) {
        orderItems.remove(item);
        item.setOrder(null);
    }
    
    public void calculateTotal() {
        this.totalAmount = orderItems.stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : null) +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", numberOfItems=" + (orderItems != null ? orderItems.size() : 0) +
                '}';
    }
} 