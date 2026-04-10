package com.webcacanh.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "import_records")
public class ImportRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "import_date")
    private LocalDate importDate;

    private String categoryName;
    private Integer importQuantity;
    private Double totalImportPrice;
    private Integer totalProducts;
    private Integer soldQuantity;
    private Integer stockQuantity;

    public ImportRecord() {}

    // --- GETTERS & SETTERS (Bắt buộc phải có để hiển thị lên web) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getImportDate() { return importDate; }
    public void setImportDate(LocalDate importDate) { this.importDate = importDate; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Integer getImportQuantity() { return importQuantity; }
    public void setImportQuantity(Integer importQuantity) { this.importQuantity = importQuantity; }

    public Double getTotalImportPrice() { return totalImportPrice; }
    public void setTotalImportPrice(Double totalImportPrice) { this.totalImportPrice = totalImportPrice; }

    public Integer getTotalProducts() { return totalProducts; }
    public void setTotalProducts(Integer totalProducts) { this.totalProducts = totalProducts; }

    public Integer getSoldQuantity() { return soldQuantity; }
    public void setSoldQuantity(Integer soldQuantity) { this.soldQuantity = soldQuantity; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
}