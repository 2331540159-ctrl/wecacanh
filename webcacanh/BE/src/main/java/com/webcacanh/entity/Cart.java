package com.webcacanh.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1. Mỗi user chỉ có 1 cart
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // 2. Thời gian tạo
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 3. Danh sách sản phẩm trong giỏ
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    // ===== Auto set thời gian =====
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ===== Constructor =====
    public Cart() {}

    public Cart(User user) {
        this.user = user;
    }

    // ===== Helper methods (QUAN TRỌNG) =====

    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }

    public void clearItems() {
        for (CartItem item : items) {
            item.setCart(null);
        }
        items.clear();
    }

    // ===== Getter & Setter =====

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}