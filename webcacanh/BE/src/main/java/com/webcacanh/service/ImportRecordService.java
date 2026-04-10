package com.webcacanh.service;

import com.webcacanh.entity.ImportRecord;
import com.webcacanh.repository.ImportRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ImportRecordService {

    @Autowired
    private ImportRecordRepository importRepo;

    public List<ImportRecord> getAllRecords() {
        return importRepo.findAll();
    }


    public Double getTotalImportCost() {
        Double total = importRepo.sumTotalImportPrice();
        return (total != null) ? total : 0.0;
    }
    public void save(ImportRecord record) {
        importRepo.save(record);
}
}