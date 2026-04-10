package com.webcacanh.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "receiver_name")
    private String receiverName;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    // ===== CONSTRUCTORS =====
    public Address() {
    }

    public Address(Long userId, String receiverName, String phone, String address) {
        this.userId = userId;
        this.receiverName = receiverName;
        this.phone = phone;
        this.address = address;
    }

    // ===== GETTER SETTER =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}