package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import java.util.List;
import java.math.BigDecimal;

public interface CartService {
    List<CartItem> getCartItems();
    void addItem(Book book);
    void updateQuantity(Long bookId, int quantity);
    void removeItem(Long bookId);
    BigDecimal getTotalAmount();
    void clear();
} 