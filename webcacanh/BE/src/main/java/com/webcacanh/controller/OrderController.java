package com.webcacanh.controller;

import com.webcacanh.entity.Order;
import com.webcacanh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ===== DANH SÁCH ĐƠN HÀNG USER =====
    @GetMapping
    public String getUserOrders(Model model) {

        List<Order> orders = orderService.getAll(); // sau này filter user

        model.addAttribute("orders", orders);
        return "orders";
    }

    // ===== CHI TIẾT ĐƠN HÀNG =====
    @GetMapping("/{id}")
    public String getOrderDetail(@PathVariable Long id, Model model) {

        try {
            Order order = orderService.getById(id);

            model.addAttribute("order", order);
            return "order-detail";

        } catch (Exception e) {
            return "redirect:/orders";
        }
    }
}