package com.webcacanh.controller;

import com.webcacanh.entity.Product;
import com.webcacanh.entity.Promotion;
import com.webcacanh.repository.ProductRepository;
import com.webcacanh.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/product-discount")
public class AdminProductDiscountController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    // ================== LIST ==================
    @GetMapping
    public String list(Model model) {

        // chỉ lấy sản phẩm đang có giảm giá
        List<Product> products = productRepository.findAll()
                .stream()
                .filter(p -> p.getPromotion() != null)
                .collect(Collectors.toList());

        model.addAttribute("discounts", products);
        return "admin-product-discount";
    }

    // ================== ADD PAGE ==================
    @GetMapping("/add")
    public String add(Model model) {

        // chỉ lấy sản phẩm CHƯA có giảm giá
        List<Product> products = productRepository.findAll()
                .stream()
                .filter(p -> p.getPromotion() == null)
                .collect(Collectors.toList());

        model.addAttribute("products", products);
        model.addAttribute("promotions", promotionRepository.findAll());

        // ✅ FIX tên file đúng
        return "admin-add-product-discount";
    }

    // ================== SAVE ==================
    @PostMapping("/save")
    public String save(@RequestParam Long productId,
                       @RequestParam Long promotionId,
                       RedirectAttributes ra) {

        try {
            Product product = productRepository.findById(productId).orElse(null);
            Promotion promotion = promotionRepository.findById(promotionId).orElse(null);

            // check null
            if (product == null || promotion == null) {
                ra.addFlashAttribute("error", "Dữ liệu không hợp lệ!");
                return "redirect:/admin/product-discount/add";
            }

            // ❗ chống gán lại discount
            if (product.getPromotion() != null) {
                ra.addFlashAttribute("error", "Sản phẩm đã có giảm giá!");
                return "redirect:/admin/product-discount/add";
            }

            // ❗ không update ngày ở đây (tránh lỗi hết hạn)
            product.setPromotion(promotion);

            productRepository.save(product);

            ra.addFlashAttribute("message", "Áp dụng giảm giá thành công!");

        } catch (Exception e) {
            ra.addFlashAttribute("error", "Lỗi khi áp dụng giảm giá!");
        }

        return "redirect:/admin/product-discount";
    }

    // ================== REMOVE ==================
    @GetMapping("/remove/{id}")
    public String removeDiscount(@PathVariable Long id, RedirectAttributes ra) {

        try {
            Product product = productRepository.findById(id).orElse(null);

            if (product != null) {
                product.setPromotion(null);
                productRepository.save(product);
                ra.addFlashAttribute("message", "Đã gỡ bỏ giảm giá!");
            }

        } catch (Exception e) {
            ra.addFlashAttribute("error", "Lỗi khi gỡ bỏ!");
        }

        return "redirect:/admin/product-discount";
    }

    // ================== (OPTIONAL) ==================
    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long id,
                               @RequestParam String status) {
        return "redirect:/admin/product-discount";
    }
}