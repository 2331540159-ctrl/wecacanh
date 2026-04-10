package com.webcacanh.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== USER (FIX LỖI CHÍNH Ở ĐÂY) =====
    @ManyToOne
    @JoinColumn(name = "user_id") // map với DB
    private User user;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "total_amount")
    private Double total;

    private Integer status;

    @Column(name = "created_at", insertable = true, updatable = false)
    private LocalDateTime createdAt;

    // ===== TRANSIENT (HIỂN THỊ) =====

    @Transient
    private String customerName;

    @Transient
    private Double subtotal;

    @Transient
    private Double shippingFee;

    @Transient
    private String phone;

    @Transient
    private String email;

    @Transient
    private String address;

    @Transient
    private String paymentMethod;

    // ===== RELATION =====
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    // ===== AUTO FIELD =====
    @PrePersist
    public void prePersist() {
        if (this.status == null) this.status = 0;
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
        if (this.shippingFee == null) this.shippingFee = 0.0;
    }

    @PostLoad
    public void calculateSubtotal() {
        if (this.total != null) {
            this.subtotal = this.total - (this.shippingFee != null ? this.shippingFee : 0.0);
        }
    }

    // ===== GETTER SETTER =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // ✅ USER
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Double getShippingFee() { return shippingFee; }
    public void setShippingFee(Double shippingFee) { this.shippingFee = shippingFee; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
}