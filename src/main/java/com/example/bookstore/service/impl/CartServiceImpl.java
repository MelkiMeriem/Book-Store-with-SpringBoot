package com.example.bookstore.service.impl;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.service.CartService;
import com.example.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    
    private final CartItemRepository cartItemRepository;
    private final UserService userService;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        
        if (auth.getPrincipal() instanceof User) {
            User user = (User) auth.getPrincipal();
            return userService.findById(user.getId()); // Get a fresh copy from the database
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItem> getCartItems() {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return Collections.emptyList();
        }
        return cartItemRepository.findByUser(currentUser);
    }

    @Override
    @Transactional
    public void addItem(Book book) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return;
        }
        
        cartItemRepository.findByUserAndBook_Id(currentUser, book.getId())
            .ifPresentOrElse(
                cartItem -> {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    cartItemRepository.save(cartItem);
                },
                () -> cartItemRepository.save(new CartItem(currentUser, book, 1))
            );
    }

    @Override
    @Transactional
    public void updateQuantity(Long bookId, int quantity) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return;
        }

        cartItemRepository.findByUserAndBook_Id(currentUser, bookId)
            .ifPresent(cartItem -> {
                if (quantity > 0) {
                    cartItem.setQuantity(quantity);
                    cartItemRepository.save(cartItem);
                } else {
                    cartItemRepository.delete(cartItem);
                }
            });
    }

    @Override
    @Transactional
    public void removeItem(Long bookId) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return;
        }

        cartItemRepository.findByUserAndBook_Id(currentUser, bookId)
            .ifPresent(cartItemRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalAmount() {
        return getCartItems().stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional
    public void clear() {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return;
        }
        cartItemRepository.deleteByUser(currentUser);
    }
} 