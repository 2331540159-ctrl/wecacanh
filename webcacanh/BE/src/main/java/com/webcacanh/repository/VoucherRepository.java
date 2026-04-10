package com.webcacanh.repository;

import com.webcacanh.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    boolean existsByCode(String code);
    
    // Tìm mã còn hoạt động và còn số lượng
    Optional<Voucher> findByCodeAndStatusAndQuantityGreaterThan(String code, String status, Integer quantity);
    
    List<Voucher> findAllByOrderByIdDesc();
}