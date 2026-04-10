package com.webcacanh.repository;

import com.webcacanh.entity.ImportRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportRecordRepository extends JpaRepository<ImportRecord, Long> {
    
    // Tính tổng tiền nhập hàng từ tất cả các bản ghi
    @Query("SELECT SUM(i.totalImportPrice) FROM ImportRecord i")
    Double sumTotalImportPrice();
}