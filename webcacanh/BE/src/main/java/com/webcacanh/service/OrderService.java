package com.webcacanh.service;

import com.webcacanh.entity.Order;
import com.webcacanh.entity.OrderItem;
import com.webcacanh.repository.OrderRepository;
import com.webcacanh.repository.AddressRepository;
import com.webcacanh.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * ===== LẤY DANH SÁCH ĐƠN HÀNG (ADMIN) =====
     */
    public List<Order> getAll() {
        List<Order> orders = orderRepository.findAllByOrderByCreatedAtDesc();

        return orders.stream()
                .map(this::fillOrderInfo)
                .collect(Collectors.toList());
    }

    /**
     * ===== UPDATE TRẠNG THÁI =====
     */
    public void updateStatus(Long id, Integer status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng ID: " + id));

        order.setStatus(status);
        orderRepository.save(order);
    }

    /**
     * ===== XÓA ĐƠN =====
     */
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    /**
     * ===== LẤY CHI TIẾT ĐƠN HÀNG (QUAN TRỌNG NHẤT) =====
     */
    public Order getById(Long id) {

        // 🔥 LOAD KÈM ORDER ITEMS (FIX LỖI CHÍNH)
        Order order = orderRepository.findByIdWithItems(id);

        if (order == null) {
            throw new RuntimeException("Không tìm thấy đơn hàng ID: " + id);
        }

        return fillOrderDetail(order);
    }

    /**
     * ===== FILL THÔNG TIN CHO LIST =====
     */
    private Order fillOrderInfo(Order order) {

        if (order.getAddressId() != null) {
            addressRepository.findById(order.getAddressId()).ifPresent(addr -> {
                order.setCustomerName(addr.getReceiverName());
                order.setAddress(addr.getAddress());
            });
        }

        return order;
    }

    /**
     * ===== FILL CHI TIẾT ORDER =====
     */
    private Order fillOrderDetail(Order order) {

        // ===== 1. THÔNG TIN ĐỊA CHỈ =====
        if (order.getAddressId() != null) {
            addressRepository.findById(order.getAddressId()).ifPresent(addr -> {
                order.setAddress(addr.getAddress());
                order.setPhone(addr.getPhone());
                order.setCustomerName(addr.getReceiverName());
            });
        }

        // ===== 2. THÔNG TIN SẢN PHẨM + TÍNH TOTAL =====
        double total = 0;

        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {

                // Lấy thông tin product
                productRepository.findById(item.getProductId()).ifPresent(p -> {
                    item.setProductName(p.getName());

                    if (p.getImages() != null && !p.getImages().isEmpty()) {
                        item.setImage(p.getImages().get(0).getImageUrl());
                    }
                });

                // Tính tổng từng dòng
                if (item.getPrice() != null && item.getQuantity() != null) {
                    double rowTotal = item.getPrice() * item.getQuantity();
                    item.setTotal(rowTotal);
                    total += rowTotal;
                }
            }
        }

        // SET tổng đơn hàng
        order.setTotal(total);

        // ===== 3. PAYMENT =====
        if (order.getPaymentMethod() == null) {
            order.setPaymentMethod("COD (Thanh toán khi nhận hàng)");
        }

        return order;
    }
}