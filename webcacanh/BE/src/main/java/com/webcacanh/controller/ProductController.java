package com.webcacanh.controller;

import com.webcacanh.entity.Product;
import com.webcacanh.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping({"/", "/index"})
    public String viewProducts(Model model) {
        model.addAttribute("dsSanPham", productService.getAllProducts());
        return "index";
    }
    // Trang sản phẩm
    @GetMapping("/product/{id}")
    public String viewProductDetail(@PathVariable("id") Long id, Model model) {
    Product product = productService.getProductById(id);
    if (product != null) {
        model.addAttribute("product", product);
        return "product";
    }
    return "redirect:/";

    //Lọc Danh mục sp
}
@GetMapping("/category/{id}")
public String viewProductsByCategory(@PathVariable("id") Long id, Model model) {
    // 1. Lấy danh sách sản phẩm theo Category ID
    List<Product> products = productService.getProductsByCategoryId(id);
    
    // 2. Đẩy danh sách này vào khay "dsSanPham" giống hệt trang chủ
    model.addAttribute("dsSanPham", products);
    
    // 3. Trả về trang index.html để nó hiển thị danh sách vừa lọc
    return "index";
}
    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        // 1. Gọi Service để tìm kiếm
        List<Product> products = productService.searchProducts(keyword);
        
        // 2. Gửi danh sách tìm được sang giao diện
        model.addAttribute("dsSanPham", products);
        
        // 3. Gửi lại từ khóa để hiển thị lại trên ô input
        model.addAttribute("keyword", keyword);
        
        // 4. Trả về trang hiển thị danh sách sản phẩm (Ví dụ: index hoặc product)
        // Lưu ý: Tên file HTML này phải là file có dùng th:each="product : ${products}" để in ra danh sách
        return "index";
    }
}

