package com.webcacanh.service;

import com.webcacanh.entity.Cart;
import com.webcacanh.entity.CartItem;
import com.webcacanh.entity.Product;
import com.webcacanh.entity.User;
import com.webcacanh.repository.CartRepository;
import com.webcacanh.repository.CartItemRepository;
import com.webcacanh.repository.ProductRepository;
import com.webcacanh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
private UserRepository userRepository;

public Cart getCartByUserId(Long userId) {

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User không tồn tại"));

    return cartRepository.findByUser(user).orElseGet(() -> {
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    });
}

    // 2. Thêm sản phẩm vào giỏ
    @Transactional
    public void addToCart(Long userId, Long productId, int quantity) {

        Cart cart = getCartByUserId(userId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // kiểm tra đã tồn tại chưa
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);

            cart.addItem(newItem); // QUAN TRỌNG
        }

        cartRepository.save(cart); // save cascade luôn CartItem
    }

    // 3. Update số lượng
    @Transactional
    public void updateQuantity(Long itemId, int delta) {

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy item"));

        int newQuantity = item.getQuantity() + delta;

        if (newQuantity <= 0) {
            item.getCart().removeItem(item); // dùng helper
        } else {
            item.setQuantity(newQuantity);
        }

        cartItemRepository.save(item);
    }

    // 4. Xóa item
    @Transactional
    public void remove(Long itemId) {

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy item"));

        item.getCart().removeItem(item);
    }

    // 5. Clear cart
    @Transactional
    public void clear(Long userId) {

        Cart cart = getCartByUserId(userId);
        cart.clearItems(); // dùng helper

        cartRepository.save(cart);
    }

    // 6. Tổng tiền
    public double getTotalPrice(Long userId) {

        Cart cart = getCartByUserId(userId);

        return cart.getItems().stream()
                .mapToDouble(item ->
                        item.getProduct().getFinalPrice() * item.getQuantity()
                )
                .sum();
    }
}