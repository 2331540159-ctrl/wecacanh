package com.webcacanh.controller;

import com.webcacanh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user") // Nên để số nhiều "users" cho đồng bộ với file HTML
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        // 1. Lấy dữ liệu từ Service
        // 2. Đưa vào model để Thymeleaf nhận được biến "users"
        model.addAttribute("users", userService.getAllUsers());
        
        // 3. TRẢ VỀ TÊN FILE HTML (không có chữ redirect:)
        return "admin-user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        // Sau khi xóa xong thì mới dùng redirect để quay lại trang danh sách
        return "redirect:/admin/user";
    }
}