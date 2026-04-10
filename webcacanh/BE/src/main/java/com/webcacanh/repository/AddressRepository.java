package com.webcacanh.repository; // 1. Phải khớp với thư mục vật lý

import com.webcacanh.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 2. Phải có dòng này
public interface AddressRepository extends JpaRepository<Address, Long> {
}