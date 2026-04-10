package com.webcacanh.repository;

import com.webcacanh.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    List<OrderHistory> findByUserId(Long userId);

}