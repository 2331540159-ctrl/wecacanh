package com.webcacanh.service;

import com.webcacanh.entity.User;
import com.webcacanh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ĐĂNG KÝ
    public User register(String name, String email, String password) {
        // Kiểm tra xem email đã tồn tại chưa bằng Optional
        Optional<User> existingUser = userRepository.findByEmail(email);
        
        if (existingUser.isPresent()) {
            return null; // Email đã tồn tại
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password); // Lưu ý: thực tế nên dùng BCryptPasswordEncoder
        user.setRole("USER");
        
        return userRepository.save(user);
    }

    // ĐĂNG NHẬP
    public User login(String email, String password) {
        // Lấy User ra khỏi Optional nếu có
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null); 
    }

    // CẬP NHẬT THÔNG TIN
    public User updateProfile(User currentUser, String name, String phone) {
        // currentUser ở đây nên được lấy lại từ DB để đảm bảo dữ liệu mới nhất
        return userRepository.findById(currentUser.getId()).map(user -> {
            user.setName(name);
            user.setPhone(phone);
            return userRepository.save(user);
        }).orElse(currentUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}