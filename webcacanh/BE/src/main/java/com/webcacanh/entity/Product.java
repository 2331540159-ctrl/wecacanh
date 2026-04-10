package com.webcacanh.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    private Integer stock;

    // 1 = active, 0 = inactive
    private Integer status = 1;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ===== RELATIONSHIPS =====

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    // ===== AUTO TIME =====
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Product() {}

    // ===== HELPER METHODS =====

    // 1. Lấy ảnh chính
    public String getMainImageUrl() {
        if (images != null && !images.isEmpty()) {
            return images.get(0).getImageUrl(); // FIX tên field
        }
        return "default.jpg";
    }

    // 2. Giá sau giảm
    public Double getFinalPrice() {
        if (price == null) return 0.0;

        if (promotion != null && promotion.getDiscountPercent() != null) {
            return price * (1 - promotion.getDiscountPercent() / 100.0);
        }
        return price;
    }

    // 3. % giảm giá
    public Integer getDiscountPercentValue() {
        if (promotion != null && promotion.getDiscountPercent() != null) {
            return promotion.getDiscountPercent().intValue();
        }
        return 0;
    }

    // 4. Kiểm tra còn hàng
    public boolean isInStock() {
        return stock != null && stock > 0;
    }

    // 5. Kiểm tra active
    public boolean isActive() {
        return status != null && status == 1;
    }

    // 6. Thêm/xóa ảnh (QUAN TRỌNG)
    public void addImage(ProductImage image) {
        images.add(image);
        image.setProduct(this);
    }

    public void removeImage(ProductImage image) {
        images.remove(image);
        image.setProduct(null);
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price != null ? price : 0.0;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock != null ? stock : 0;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status != null ? status : 0;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}