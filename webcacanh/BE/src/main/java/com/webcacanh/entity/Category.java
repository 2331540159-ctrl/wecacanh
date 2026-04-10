package com.webcacanh.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Một danh mục có nhiều sản phẩm
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category() {}

    // Getter và Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}