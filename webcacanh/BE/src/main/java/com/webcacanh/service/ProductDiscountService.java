package com.webcacanh.service;

import com.webcacanh.entity.Product;
import com.webcacanh.entity.Promotion;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service("productDiscountService")
public class ProductDiscountService {

    // Tính giá cuối cùng sau khi giảm %
    public Double getFinalPrice(Product p) {
        if (p.getPromotion() == null || !isPromotionActive(p.getPromotion())) {
            return p.getPrice();
        }
        double discount = p.getPromotion().getDiscountPercent() / 100.0;
        return p.getPrice() * (1 - discount);
    }

    // Xác định trạng thái của chương trình giảm giá
    public String getStatus(Product p) {
        if (p.getPromotion() == null) return "KHÔNG CÓ";
        
        LocalDate now = LocalDate.now();
        Promotion promo = p.getPromotion();
        
        if (promo.getStartDate() != null && now.isBefore(promo.getStartDate())) {
            return "SẮP DIỄN RA";
        }
        if (promo.getEndDate() != null && now.isAfter(promo.getEndDate())) {
            return "ĐÃ KẾT THÚC";
        }
        return "ACTIVE";
    }

    private boolean isPromotionActive(Promotion promo) {
        LocalDate now = LocalDate.now();
        boolean started = (promo.getStartDate() == null || !now.isBefore(promo.getStartDate()));
        boolean notEnded = (promo.getEndDate() == null || !now.isAfter(promo.getEndDate()));
        return started && notEnded;
    }
}