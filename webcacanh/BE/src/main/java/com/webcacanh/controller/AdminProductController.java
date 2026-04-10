package com.webcacanh.controller;

import com.webcacanh.entity.Product;
import com.webcacanh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminProductController {

    @Autowired
    private ProductService productService;

    // 1. Đổi URL cho khớp với th:href trong HTML
    @GetMapping("/admin/products") 
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        
        // 2. Trả thẳng về tên file HTML (không có .html và TUYỆT ĐỐI KHÔNG dùng redirect)
        return "admin-products";
    }
}