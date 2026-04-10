package com.webcacanh.controller;

import com.webcacanh.entity.Voucher;
import com.webcacanh.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/vouchers") // <--- PHẢI CÓ CHỮ 's' Ở ĐÂY
public class AdminVoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("vouchers", voucherService.getAll());
        return "admin-vouchers";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("voucher", new Voucher()); 
        return "admin-add-voucher";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("voucher") Voucher voucher, RedirectAttributes ra) {
        try {
            voucherService.save(voucher);
            ra.addFlashAttribute("message", "Lưu voucher thành công!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
            // Đã khớp với /admin/vouchers phía trên
            return "redirect:/admin/vouchers/add"; 
        }
        return "redirect:/admin/vouchers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            voucherService.delete(id);
            ra.addFlashAttribute("message", "Xóa thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Không thể xóa!");
        }
        return "redirect:/admin/vouchers";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Voucher v = voucherService.getById(id);
        if (v == null) return "redirect:/admin/vouchers";
        model.addAttribute("voucher", v); 
        return "admin-add-voucher";
    }
}