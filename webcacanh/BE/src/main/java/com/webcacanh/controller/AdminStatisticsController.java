package com.webcacanh.controller;

// Các thư viện Spring Boot bắt buộc
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// Thư viện để làm việc với List và Service của bạn
import java.util.List;
import com.webcacanh.entity.ImportRecord;
import com.webcacanh.repository.ImportRecordRepository;
import com.webcacanh.service.ImportRecordService;

@Controller
@RequestMapping("/admin")
public class AdminStatisticsController {

    @Autowired
    private ImportRecordService importService;

    @GetMapping("/statistics")
    public String viewStatistics(Model model) {
        // 1. Lấy danh sách phiếu nhập để hiển thị ra bảng
        List<ImportRecord> list = importService.getAllRecords();
        model.addAttribute("statistics", list); // Nhớ dùng đúng tên 

        // 2. Tính toán các con số cho 4 ô Card tổng quát
        Double tongTienNhap = importService.getTotalImportCost();
        Double tongDoanhThu = 15000000.0; // Tạm thời để số cứng để test giao diện
        Double loiNhuan = tongDoanhThu - tongTienNhap;

        // Đẩy dữ liệu ra giao diện
        model.addAttribute("tongTienNhap", tongTienNhap);
        model.addAttribute("tongDoanhThu", tongDoanhThu);
        model.addAttribute("loiNhuan", loiNhuan);

        return "admin-statistics"; // Tên file HTML của bạn (không cần đuôi .html)
    }
    // 1. Hiển thị trang form thêm mới
@GetMapping("/statistics/add")
public String showAddForm(Model model) {
    // Tạo một đối tượng rỗng để Thymeleaf binding vào Form
    model.addAttribute("importRecord", new ImportRecord());
    return "admin-statistics-add"; // Phải khớp với tên file .html của bạn
}

// 2. Xử lý lưu dữ liệu khi người dùng nhấn "Lưu" trên Form
@PostMapping("/statistics/save")
public String saveImportRecord(@ModelAttribute("importRecord") ImportRecord record) {
    // Logic tự động tính toán số tồn kho ban đầu
    if (record.getImportQuantity() != null) {
        record.setTotalProducts(record.getImportQuantity());
        record.setStockQuantity(record.getImportQuantity());
        record.setSoldQuantity(0);
    }
    
    // Gọi service để lưu xuống DB
    importService.save(record);
    
    // Lưu xong thì quay lại trang danh sách thống kê
    return "redirect:/admin/statistics";
}
@Controller
@RequestMapping("/admin/statistics")
public class StatisticsController {

    @Autowired
    private ImportRecordRepository repository; // Hoặc Service của bạn

    // 1. Mapping cho nút Sửa (Mở form edit)
    @GetMapping("/edit/{id}")
    public String editRecord(@PathVariable("id") Long id, Model model) {
        ImportRecord record = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        model.addAttribute("record", record);
        return "admin-statistics-edit"; // Trả về file HTML trang sửa
    }

    // 2. Mapping cho nút Xóa
    @GetMapping("/delete/{id}")
    public String deleteRecord(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:/admin/statistics"; // Xóa xong quay lại trang danh sách
    }
}
}