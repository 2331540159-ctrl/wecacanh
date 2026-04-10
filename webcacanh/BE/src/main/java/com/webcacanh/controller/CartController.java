package com.webcacanh.controller;

import com.webcacanh.entity.Cart;
import com.webcacanh.service.CartService;
import com.webcacanh.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private VoucherService voucherService;

    // ID người dùng giả định (Trong thực tế sẽ lấy từ SecurityContextHolder)
    private final Long CURRENT_USER_ID = 2L;

    // 1. Hiển thị giỏ hàng
    @GetMapping
    public String viewCart(Model model) {
        Cart cart = cartService.getCartByUserId(CURRENT_USER_ID);
        
        // Đưa dữ liệu vào model khớp với các biến trong file cart.html
        model.addAttribute("cartItems", cart.getItems()); 
        model.addAttribute("totalPrice", cartService.getTotalPrice(CURRENT_USER_ID)); 
        model.addAttribute("vouchers", voucherService.getAll()); 
        
        // --- THÔNG TIN BỔ SUNG CHO HEADER/FOOTER ---
        model.addAttribute("hotline", "0900.000.000");
        model.addAttribute("address", "123 Đường ABC, Quận 1, TP. HCM");
        model.addAttribute("cartCount", cart.getItems().size());
        
        // Fix lỗi nội dung banner bị trống trong ảnh bạn gửi
        model.addAttribute("footerBannerText", "🔥 Khuyến mãi đặc biệt: Nhập mã GUPPY50 để được giảm giá 50% cho đơn hàng đầu tiên!");

        return "cart"; 
    }

    // 2. Thêm sản phẩm vào giỏ (Dùng cho nút "Mua ngay" hoặc "Thêm vào giỏ")
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, 
                           @RequestParam(defaultValue = "1") int quantity) {
        cartService.addToCart(CURRENT_USER_ID, productId, quantity); 
        return "redirect:/cart"; 
    }

    // 3. Cập nhật số lượng qua AJAX (Dùng cho nút + / - trong cart.html)
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateQuantity(@RequestParam Long itemId, @RequestParam int delta) {
        try {
            cartService.updateQuantity(itemId, delta);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi cập nhật: " + e.getMessage());
        }
    }

    // 4. Xóa một món hàng khỏi giỏ
    @PostMapping("/remove")
    public String removeItem(@RequestParam("cartItemId") Long id) {
        cartService.remove(id);
        return "redirect:/cart";
    }

    // 5. Xóa toàn bộ giỏ hàng
    @GetMapping("/clear")
    public String clearCart() {
        cartService.clear(CURRENT_USER_ID);
        return "redirect:/cart";
    }
}