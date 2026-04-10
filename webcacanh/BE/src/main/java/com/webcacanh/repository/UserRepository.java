package com.webcacanh.repository;

import com.webcacanh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm kiếm người dùng bằng email (Dùng cho Đăng nhập/Check tồn tại)
    Optional<User> findByEmail(String email);

    // Kiểm tra xem email đã tồn tại hay chưa
    Boolean existsByEmail(String email);
    
}