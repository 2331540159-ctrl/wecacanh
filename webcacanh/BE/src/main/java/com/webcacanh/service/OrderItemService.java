package com.webcacanh.service;

import com.webcacanh.entity.Order;
import com.webcacanh.entity.OrderItem;
import com.webcacanh.repository.OrderItemRepository;
import com.webcacanh.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    // ===== LẤY DANH SÁCH ORDER ITEM =====
    public List<OrderItem> getAll() {
        return orderItemRepository.findAll();
    }

    // ===== LẤY THEO ID =====
    public OrderItem getById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found: " + id));
    }

    // ===== LẤY DANH SÁCH ITEM THEO ORDER =====
    public List<OrderItem> getByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    // ===== TẠO ORDER ITEM =====
    public OrderItem create(Long orderId, OrderItem item) {
        // Lấy Order từ DB (BẮT BUỘC)
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        // GÁN RELATIONSHIP
        item.setOrder(order);

        return orderItemRepository.save(item);
    }

    // ===== UPDATE =====
    public OrderItem update(Long id, OrderItem newItem) {
        OrderItem oldItem = getById(id);

        oldItem.setProductId(newItem.getProductId());
        oldItem.setQuantity(newItem.getQuantity());
        oldItem.setPrice(newItem.getPrice());

        return orderItemRepository.save(oldItem);
    }

    // ===== DELETE =====
    public void delete(Long id) {
        orderItemRepository.deleteById(id);
    }

    // ===== TÍNH TỔNG TIỀN THEO ORDER =====
    public Double getTotalByOrderId(Long orderId) {
        List<OrderItem> items = getByOrderId(orderId);

        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}