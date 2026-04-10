package com.webcacanh.repository;

import com.webcacanh.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1. Tìm theo Category ID (Dùng _Id để JPA hiểu là lấy ID của object Category)
    List<Product> findByCategory_Id(Long categoryId);

    // 2. Tìm theo tên (không phân biệt hoa thường)
    List<Product> findByNameContainingIgnoreCase(String name);

    // 3. Tìm theo trạng thái (Ví dụ: 1 là đang bán, 0 là ngừng bán)
    List<Product> findByStatus(Integer status);

    // 4. Lấy 8 sản phẩm mới nhất (Dựa trên trường createdAt đã thêm ở Entity)
    List<Product> findTop8ByOrderByCreatedAtDesc();

    // 5. Phân trang sản phẩm theo Category và Status (Dùng cho trang danh mục)
    Page<Product> findByCategory_IdAndStatus(Long categoryId, Integer status, Pageable pageable);

    // 6. PHẢI SỬA: Lấy sản phẩm đang có khuyến mãi
    // Lưu ý: p.promotion IS NOT NULL (khớp với biến 'promotion' trong Product.java)
    @Query("SELECT p FROM Product p WHERE p.promotion IS NOT NULL")
    List<Product> findProductsOnSale();
    
    // 7. Tìm kiếm theo từ khóa trong tên hoặc mô tả
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:kw% OR p.description LIKE %:kw%")
    List<Product> searchByKeyword(@Param("kw") String keyword);

    // 8. Lọc sản phẩm theo khoảng giá (Bổ sung để làm bộ lọc giá)
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // 9. Lấy sản phẩm liên quan (cùng danh mục nhưng trừ sản phẩm hiện tại)
    @Query("SELECT p FROM Product p WHERE p.category.id = :cid AND p.id <> :pid")
    List<Product> findRelatedProducts(@Param("cid") Long categoryId, @Param("pid") Long productId);
    


    
}
