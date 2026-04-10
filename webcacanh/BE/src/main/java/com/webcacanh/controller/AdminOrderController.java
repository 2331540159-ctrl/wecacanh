package com.webcacanh.controller;

import com.webcacanh.entity.Order;
import com.webcacanh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    // ==========================================
    // 1. DANH SÁCH ĐƠN HÀNG
    // ==========================================
    @GetMapping("/admin/orders")
    public String list(Model model) {
        // Sử dụng getAll() đã được bổ sung logic fill thông tin cơ bản (customerName)
        List<Order> orders = orderService.getAll();
        model.addAttribute("orders", orders);
        return "admin-orders";
    }

    // ==========================================
    // 2. CẬP NHẬT TRẠNG THÁI (KHỚP VỚI SELECT TRÊN HTML)
    // ==========================================
    @PostMapping("/admin/orders/update-status")
    public String updateStatus(@RequestParam Long orderId,
                            @RequestParam String status) {
        try {
            // Ép kiểu String từ Form sang Integer để khớp với DB và Entity mới
            Integer statusInt = Integer.parseInt(status);
            orderService.updateStatus(orderId, statusInt);
        } catch (NumberFormatException e) {
            // Nếu lỗi ép kiểu, bạn có thể log lỗi ở đây
            System.out.println("Lỗi ép kiểu status: " + e.getMessage());
        }
        return "redirect:/admin/orders";
    }

    // ==========================================
    // 3. XÓA ĐƠN HÀNG
    // ==========================================
    @PostMapping("/admin/orders/delete/{id}")
    public String delete(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/admin/orders";
    }

    // ==========================================
    // 4. XEM CHI TIẾT ĐƠN HÀNG (ICON MẮT)
    // ==========================================
    @GetMapping("/admin/orders/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        // Sử dụng getById(id) - phương thức này trong OrderService 
        // đã được bạn bổ sung hàm fillOrderDetail để lấy User, Address, Items
        Order order = orderService.getById(id);
        
        model.addAttribute("order", orderService.getById(id));
        return "admin-order-detail";
    }
}