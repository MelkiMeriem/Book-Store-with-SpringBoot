package com.example.bookstore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private int quantity;

    public CartItem(User user, Book book, int quantity) {
        this.user = user;
        this.book = book;
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return book.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
} 