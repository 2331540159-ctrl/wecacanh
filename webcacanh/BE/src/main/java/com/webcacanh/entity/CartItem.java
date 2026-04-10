package com.webcacanh.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nhiều CartItem thuộc về một Cart (Giỏ hàng)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    // Mỗi CartItem ứng với một Product (Sản phẩm) cụ thể
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    public CartItem() {
    }

    // Constructor tiện lợi để khởi tạo nhanh
    public CartItem(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    // ===== Helper Method (Tính tiền cho từng dòng hàng) =====
    public Double getSubTotal() {
        if (product != null && quantity != null) {
            // Sử dụng getFinalPrice() đã bổ sung ở Product.java để tính cả khuyến mãi
            return product.getFinalPrice() * quantity;
        }
        return 0.0;
    }

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }   

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}