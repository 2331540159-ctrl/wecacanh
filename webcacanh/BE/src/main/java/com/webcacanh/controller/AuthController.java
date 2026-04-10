package com.webcacanh.controller;

import com.webcacanh.service.UserService;
import com.webcacanh.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // 1. Hiển thị trang Đăng ký
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Spring sẽ tìm file templates/register.html
    }

    // 2. Xử lý Đăng ký
    @PostMapping("/register")
    public String register(@RequestParam String name,
                        @RequestParam String email,
                        @RequestParam String password,
                           Model model) {
        User user = userService.register(name, email, password);
        if (user == null) {
            model.addAttribute("error", "Email đã tồn tại!");
            return "register"; 
        }
        return "redirect:/login";
    }

    // 3. Hiển thị trang Đăng nhập
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Spring sẽ tìm file templates/login.html
    }

    // 4. Xử lý Đăng nhập
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam String password,
                        HttpSession session, 
                        Model model) {
        User user = userService.login(email, password);

        if (user == null) {
            model.addAttribute("error", "Sai email hoặc mật khẩu!");
            return "login";
        }

        session.setAttribute("user", user);

        // Chỗ này quan trọng: Nếu bạn map trang chủ là "/" thì dùng "/"
        if ("ADMIN".equalsIgnoreCase(user.getRole().trim())) {
        return "redirect:/admindashboard"; // Dùng gạch chéo cho chuyên nghiệp
}

        return "redirect:/";
    }
    @GetMapping("/admindashboard") // Phải khớp với cái link ở Bước 1
public String showAdminPage(HttpSession session) {
    User user = (User) session.getAttribute("user");
    // Bảo mật: Ko phải admin thì cho ra đảo
    if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole().trim())) {
        return "redirect:/login";
    }
    return "admindashboard";
}

    // 5. Đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login"; // Nên redirect thay vì return thẳng view
    }
}