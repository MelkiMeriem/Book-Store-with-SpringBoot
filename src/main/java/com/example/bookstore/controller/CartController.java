package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final BookService bookService;

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalAmount", cartService.getTotalAmount());
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long bookId, @RequestParam(defaultValue = "1") Integer quantity) {
        Book book = bookService.getBookById(bookId);
        for (int i = 0; i < quantity; i++) {
            cartService.addItem(book);
        }
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long bookId, @RequestParam int quantity) {
        if (quantity <= 0) {
            cartService.removeItem(bookId);
        } else {
            cartService.updateQuantity(bookId, quantity);
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long bookId) {
        cartService.removeItem(bookId);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clear();
        return "redirect:/cart";
    }
} 