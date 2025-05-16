package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "books";
    }
    
    @GetMapping("/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "book-details";
    }
    
    @GetMapping("/search")
    public String searchBooks(@RequestParam String query, Model model) {
        model.addAttribute("books", bookService.searchBooks(query));
        model.addAttribute("searchQuery", query);
        return "books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "book-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book) {
        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.createBook(book);
        return "redirect:/books";
    }
} 