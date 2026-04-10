package com.webcacanh.repository;

import com.webcacanh.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Admin: danh sách đơn hàng mới nhất
    List<Order> findAllByOrderByCreatedAtDesc();

    // User: lịch sử mua hàng
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Lọc theo trạng thái
    List<Order> findByStatusOrderByCreatedAtDesc(Integer status);

    // 🔥 QUAN TRỌNG: load kèm orderItems
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.id = :id")
    Order findByIdWithItems(@Param("id") Long id);
}