package com.example.bookstore.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "books")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(nullable = false)
    private Integer stock;
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    public Integer getStock() {
        return this.stock;
    }
} 