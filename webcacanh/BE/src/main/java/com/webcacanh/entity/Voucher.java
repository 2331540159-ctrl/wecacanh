package com.webcacanh.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
@Table(name = "voucher") // Đổi tên table cho khớp với ảnh phpMyAdmin
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String code;

    @Column(name = "discount_percent", nullable = false)
    private Integer discountPercent;

    @Column(name = "max_discount", nullable = false)
    private Double maxDiscount;

    @Column(name = "min_order_value", nullable = false)
    private Double minOrderValue;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, length = 255)
    private String status; // ACTIVE / INACTIVE

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    // Ảnh của bạn không hiển thị cột created_at, 
    // nếu bạn muốn dùng thì hãy thêm cột vào DB trước.
    // Thêm vào file Voucher.java để sửa lỗi biên dịch
public Long getId() { return id; }
public String getCode() { return code; }
public Integer getQuantity() { return quantity; }
public LocalDate getStartDate() { return startDate; }
public LocalDate getEndDate() { return endDate; }
public void setStatus(String status) { this.status = status; }
}