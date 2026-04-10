-- =====================================
-- DATABASE
-- =====================================

DROP DATABASE IF EXISTS webcacanh;
CREATE DATABASE webcacanh;
USE webcacanh;

-- =====================================
-- USERS
-- =====================================

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    phone VARCHAR(20),
    role VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================
-- ADDRESSES
-- =====================================

CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    receiver_name VARCHAR(255),
    phone VARCHAR(20),
    address TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- =====================================
-- CATEGORIES
-- =====================================

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- =====================================
-- PROMOTIONS
-- =====================================

CREATE TABLE promotions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    discount_percent DECIMAL(5,2),
    start_date DATE,
    end_date DATE
);

-- =====================================
-- PRODUCTS
-- =====================================

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT,
    promotion_id BIGINT,
    name VARCHAR(255),
    price DECIMAL(10,2),
    stock INT,
    description TEXT,
    status INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (promotion_id) REFERENCES promotions(id)
);

-- =====================================
-- PRODUCT IMAGES
-- =====================================

CREATE TABLE product_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    image_url VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- =====================================
-- CARTS
-- =====================================

CREATE TABLE carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- =====================================
-- CART ITEMS
-- =====================================

CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT,
    product_id BIGINT,
    quantity INT,
    FOREIGN KEY (cart_id) REFERENCES carts(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- =====================================
-- ORDERS
-- =====================================

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    address_id BIGINT,
    total_amount DECIMAL(10,2),
    status INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (address_id) REFERENCES addresses(id)
);

-- =====================================
-- ORDER ITEMS
-- =====================================

CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT,
    price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- =====================================
-- PAYMENTS
-- =====================================

CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    method VARCHAR(50),
    status INT,
    paid_at TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- =====================================
-- REVIEWS
-- =====================================

CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    product_id BIGINT,
    rating INT,
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- =====================================
-- NEWS
-- =====================================

CREATE TABLE news (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================
-- BANNERS
-- =====================================

CREATE TABLE banners (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    image_url VARCHAR(255),
    link_url VARCHAR(255),
    position INT,
    is_active INT,
    start_date DATE,
    end_date DATE
);

-- =====================================
-- LOGIN LOGS
-- =====================================

CREATE TABLE login_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    ip_address VARCHAR(255),
    user_agent VARCHAR(255),
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status INT
);

-- =====================================
-- ACTIVITY LOGS
-- =====================================

CREATE TABLE activity_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    action VARCHAR(255),
    table_name VARCHAR(100),
    record_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- =====================================
-- ORDER HISTORY (LICH SU DON HANG)
-- =====================================

CREATE TABLE order_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    status INT,
    note VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_orderhistory_order
        FOREIGN KEY (order_id) REFERENCES orders(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_orderhistory_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;


CREATE TABLE `vouchers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `discount_percent` int(11) NOT NULL,
  `end_date` date DEFAULT NULL,
  `max_discount` double NOT NULL,
  `min_order_value` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `start_date` date DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- =====================================
-- SAMPLE DATA
-- =====================================

-- USERS
INSERT INTO users (name,email,password,phone,role) VALUES
('Admin','admin@gmail.com','123456','0900000001','ADMIN'),
('Huynh Chi Danh','danh@gmail.com','123456','0900000002','USER'),
('Trinh Thi Thu Thaoi','thao@gmail.com','123456','0900000003','USER');

-- ADDRESSES
INSERT INTO addresses (user_id,receiver_name,phone,address) VALUES
(2,'Huynh Chi Danh','0900000002','TP HCM'),
(3,'Trinh Thi Thu Thao','0900000003','Ha Noi');

-- CATEGORIES
INSERT INTO categories (name) VALUES
('Ca Koi'),
('Ca Betta'),
('Thuc an cho ca'),
('Phu kien be ca');

-- PROMOTIONS
INSERT INTO promotions (title,discount_percent,start_date,end_date) VALUES
('Khuyen mai he',10,'2025-06-01','2025-07-01'),
('Sale lon',20,'2025-12-01','2025-12-31');

-- PRODUCTS
INSERT INTO products (category_id,promotion_id,name,price,stock,description,status) VALUES
(1,1,'Ca Koi Nhat Ban',500000,10,'Ca koi dep',1),
(2,NULL,'Ca Betta Do',150000,20,'Ca betta dep',1),
(3,NULL,'Thuc an ca cao cap',50000,50,'Thuc an tot cho ca',1),
(4,NULL,'May loc nuoc',300000,15,'May loc ho ca',1);

-- PRODUCT IMAGES
INSERT INTO product_images (product_id,image_url) VALUES
(1,'koi1.jpg'),
(2,'betta1.jpg'),
(3,'food1.jpg'),
(4,'filter1.jpg');

-- CART
INSERT INTO carts (user_id) VALUES (2);

-- CART ITEMS
INSERT INTO cart_items (cart_id,product_id,quantity) VALUES
(1,1,1),
(1,3,2);

-- ORDERS
INSERT INTO orders (user_id,address_id,total_amount,status) VALUES
(2,1,600000,1);

-- ORDER ITEMS
INSERT INTO order_items (order_id,product_id,quantity,price) VALUES
(1,1,1,500000),
(1,3,2,50000);

-- PAYMENTS
INSERT INTO payments (order_id,method,status,paid_at) VALUES
(1,'COD',1,NOW());
-- ORDER HISTORY
INSERT INTO order_history (order_id,user_id,status,note) VALUES
(1,2,0,'Dat hang thanh cong'),
(1,1,1,'Admin da xac nhan don'),
(1,1,2,'Dang giao hang'),
(1,1,3,'Da giao thanh cong');

-- REVIEWS
INSERT INTO reviews (user_id,product_id,rating,comment) VALUES
(2,1,5,'Ca dep va khoe');

-- NEWS
INSERT INTO news (title,content,image_url) VALUES
('Cach cham soc ca koi','Huong dan cham soc ca koi','news1.jpg');

-- BANNERS
INSERT INTO banners (title,image_url,link_url,position,is_active,start_date,end_date) VALUES
('Banner Trang Chu','banner1.jpg','/products',1,1,'2025-01-01','2025-12-31');
