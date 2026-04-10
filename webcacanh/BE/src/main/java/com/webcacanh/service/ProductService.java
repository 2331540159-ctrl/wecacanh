package com.webcacanh.service;

import com.webcacanh.entity.Product;
import com.webcacanh.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 1. Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. Tìm sản phẩm theo ID
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // 3. Lấy sản phẩm theo danh mục (Đã sửa tên hàm để khớp với Repository mới)
    public List<Product> getProductsByCategoryId(Long categoryId) {
        // Sử dụng findByCategory_Id để khớp với quan hệ @ManyToOne trong Entity
        return productRepository.findByCategory_Id(categoryId);
    }

    // 4. Lấy 8 sản phẩm mới nhất cho trang chủ
    public List<Product> getTop8NewArrivals() {
        return productRepository.findTop8ByOrderByCreatedAtDesc();
    }

    // 5. Lấy sản phẩm đang khuyến mãi
    public List<Product> getProductsOnSale() {
        return productRepository.findProductsOnSale();
    }

    // 6. Tìm kiếm sản phẩm theo từ khóa
    public List<Product> searchProducts(String keyword) {
        return productRepository.searchByKeyword(keyword);
    }

    // 7. Lọc sản phẩm theo danh mục có phân trang (Dùng cho trang danh sách)
    public Page<Product> getProductsPage(Long categoryId, Integer status, Pageable pageable) {
        return productRepository.findByCategory_IdAndStatus(categoryId, status, pageable);
        
        
    }
}