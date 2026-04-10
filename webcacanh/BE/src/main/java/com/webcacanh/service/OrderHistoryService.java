package com.webcacanh.service;

import com.webcacanh.entity.OrderHistory;
import com.webcacanh.repository.OrderHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHistoryService {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    public List<OrderHistory> getAllOrderHistory() {
        return orderHistoryRepository.findAll();
    }

    public List<OrderHistory> getOrderHistoryByUser(Long userId) {
        return orderHistoryRepository.findByUserId(userId);
    }
}