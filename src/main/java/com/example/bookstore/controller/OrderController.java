package com.example.bookstore.controller;

import com.example.bookstore.model.Order;
import com.example.bookstore.model.User;
import com.example.bookstore.service.CartService;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public String viewOrders(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("orders", orderService.getOrdersByUser(user));
        return "orders";
    }

    @PostMapping("/create")
    public String createOrder(@AuthenticationPrincipal User user) {
        // Get a fresh copy of the user from the database
        User freshUser = userService.findById(user.getId());
        Order order = orderService.createOrder(freshUser, cartService.getCartItems());
        cartService.clear(); // Clear the cart after successful order creation
        return "redirect:/orders";
    }
} 