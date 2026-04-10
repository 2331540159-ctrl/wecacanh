package com.webcacanh.service;

import com.webcacanh.entity.Voucher;
import com.webcacanh.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public List<Voucher> getAll() {
        List<Voucher> list = voucherRepository.findAllByOrderByIdDesc();
        // Kiểm tra và cập nhật trạng thái cho từng voucher trong danh sách
        if (list != null) {
            list.forEach(this::updateVoucherStatus);
        }
        return list;
    }

    public void save(Voucher v) {
        // Xử lý kiểm tra mã trùng
        if (v.getId() == null && voucherRepository.existsByCode(v.getCode())) {
            throw new RuntimeException("Mã voucher '" + v.getCode() + "' đã tồn tại!");
        }
        
        updateVoucherStatus(v);
        voucherRepository.save(v);
    }

    // Phải là private void và nhận vào đối tượng Voucher
    private void updateVoucherStatus(Voucher v) {
        LocalDate now = LocalDate.now();
        if (v.getQuantity() != null && v.getQuantity() <= 0 || 
           (v.getEndDate() != null && v.getEndDate().isBefore(now)) ||
           (v.getStartDate() != null && v.getStartDate().isAfter(now))) {
            v.setStatus("INACTIVE");
        } else {
            v.setStatus("ACTIVE");
        }
    }

    public void delete(Long id) {
        voucherRepository.deleteById(id);
    }

    public Voucher getById(Long id) {
        return voucherRepository.findById(id).orElse(null);
    }
    
}