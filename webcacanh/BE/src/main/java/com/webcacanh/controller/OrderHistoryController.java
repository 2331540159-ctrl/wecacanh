package com.webcacanh.controller;

import com.webcacanh.entity.OrderHistory;
import com.webcacanh.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;

    @GetMapping("/order-history")
    public String orderHistoryPage(Model model) {

        List<OrderHistory> list = orderHistoryService.getAllOrderHistory();

        model.addAttribute("orders", list);

        return "order-history";
    }
}