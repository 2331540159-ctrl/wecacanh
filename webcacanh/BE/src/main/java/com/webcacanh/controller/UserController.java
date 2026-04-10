package com.webcacanh.controller;

import com.webcacanh.service.UserService;
import com.webcacanh.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String name, @RequestParam String phone, 
                                HttpSession session) {

        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            return "redirect:/login";
        }

        // Gọi service để cập nhật
        User updatedUser = userService.updateProfile(currentUser, name, phone);
        
        // Cập nhật lại session với dữ liệu mới nhất
        session.setAttribute("user", updatedUser);

        return "redirect:/profile";
    }
    
}