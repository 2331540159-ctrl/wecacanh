package com.webcacanh.repository;

import com.webcacanh.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    /**
     * Tìm tất cả các chương trình khuyến mãi đang trong thời gian hiệu lực
     * (Ngày bắt đầu <= hiện tại và Ngày kết thúc >= hiện tại)
     */
    @Query("SELECT p FROM Promotion p WHERE (p.startDate <= :now OR p.startDate IS NULL) " +
           "AND (p.endDate >= :now OR p.endDate IS NULL)")
    List<Promotion> findAllActivePromotions(LocalDate now);

    /**
     * Tìm khuyến mãi theo tiêu đề (nếu cần dùng cho chức năng tìm kiếm)
     */
    List<Promotion> findByTitleContainingIgnoreCase(String title);
}