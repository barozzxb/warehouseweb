create table tbl_category
(
    id          bigint auto_increment
        primary key,
    description varchar(255) null,
    name        varchar(255) not null
);

create table tbl_report
(
    id             bigint auto_increment
        primary key,
    data           tinytext     null,
    generated_date datetime(6)  not null,
    report_type    varchar(255) not null
);

create table tbl_user
(
    id       bigint auto_increment
        primary key,
    email    varchar(255)                       null,
    password varchar(255)                       not null,
    phone    varchar(255)                       null,
    role     enum ('ADMIN', 'MANAGER', 'STAFF') null,
    username varchar(255)                       not null,
    constraint UKk0bty7tbcye41jpxam88q5kj2
        unique (username)
);

create table tbl_warehouse
(
    id       bigint auto_increment
        primary key,
    location varchar(255) null,
    name     varchar(255) null
);

create table tbl_inventory
(
    id           bigint auto_increment
        primary key,
    warehouse_id bigint null,
    constraint FKfpd25s4egqyiomq35op20wxcp
        foreign key (warehouse_id) references tbl_warehouse (id)
);

create table tbl_product
(
    id           bigint auto_increment
        primary key,
    entry_date   datetime(6)    null,
    expiry_date  datetime(6)    null,
    location     varchar(255)   null,
    name         varchar(255)   not null,
    quantity     int            not null,
    unit_price   decimal(10, 2) not null,
    category_id  bigint         null,
    inventory_id bigint         null,
    constraint FKbgnhiunkmwv11vxm1g81rg8ts
        foreign key (inventory_id) references tbl_inventory (id),
    constraint FKfq7110lh85cseoy13cgni7pet
        foreign key (category_id) references tbl_category (id)
);

create table tbl_supplier
(
    id             bigint auto_increment
        primary key,
    address        varchar(255) not null,
    contact_number varchar(255) not null,
    name           varchar(255) not null,
    warehouse_id   bigint       null,
    constraint FK238mpo9dqv73scbwggom2n3ah
        foreign key (warehouse_id) references tbl_warehouse (id)
);

create table tbl_product_supplier
(
    id              bigint auto_increment
        primary key,
    supply_date     date           not null,
    supply_price    decimal(10, 2) not null,
    supply_quantity int            not null,
    product_id      bigint         null,
    supplier_id     bigint         null,
    constraint FK4d18q2fw1prkifsna5tcym4s7
        foreign key (product_id) references tbl_product (id),
    constraint FKgoe8jouowu104o9rkcxm1vtwg
        foreign key (supplier_id) references tbl_supplier (id)
);

create table tbl_transaction
(
    id               bigint auto_increment
        primary key,
    quantity         int                       not null,
    status           varchar(255)              not null,
    transaction_date datetime(6)               not null,
    type             enum ('EXPORT', 'IMPORT') not null,
    employee_id      bigint                    null,
    product_id       bigint                    null,
    supplier_id      bigint                    null,
    constraint FK64hs7jxthv4rigcupvleyn1rl
        foreign key (supplier_id) references tbl_supplier (id),
    constraint FKjdlnscmdheew7sii2jj1bkp1c
        foreign key (employee_id) references tbl_user (id),
    constraint FKqilbeuil0ffcao71ukox16m2x
        foreign key (product_id) references tbl_product (id)
);

-- ADMIN Users (10 users) - ID từ 1 đến 10
INSERT INTO tbl_user (id, username, password, email, phone, role) VALUES
(1, 'admin01', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'admin.01@example.com', '0901111111', 'ADMIN'),
(2, 'admin02', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'admin.02@example.com', '0901111112', 'ADMIN'),
(3, 'admin03', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'admin.03@example.com', '0901111113', 'ADMIN'),
(4, 'admin04', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'admin.04@example.com', '0901111114', 'ADMIN'),
(5, 'admin05', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'admin.05@example.com', '0901111115', 'ADMIN'),
(6, 'admin06', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'admin.06@example.com', '0901111116', 'ADMIN'),
(7, 'admin07', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'admin.07@example.com', '0901111117', 'ADMIN'),
(8, 'admin08', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'admin.08@example.com', '0901111118', 'ADMIN'),
(9, 'admin09', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'admin.09@example.com', '0901111119', 'ADMIN'),
(10, 'admin10', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'admin.10@example.com', '0901111120', 'ADMIN');

-- MANAGER Users (10 users) - ID từ 11 đến 20
INSERT INTO tbl_user (id, username, password, email, phone, role) VALUES
(11, 'manager01', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'manager.01@example.com', '0912222221', 'MANAGER'),
(12, 'manager02', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'manager.02@example.com', '0912222222', 'MANAGER'),
(13, 'manager03', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'manager.03@example.com', '0912222223', 'MANAGER'),
(14, 'manager04', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'manager.04@example.com', '0912222224', 'MANAGER'),
(15, 'manager05', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'manager.05@example.com', '0912222225', 'MANAGER'),
(16, 'manager06', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'manager.06@example.com', '0912222226', 'MANAGER'),
(17, 'manager07', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'manager.07@example.com', '0912222227', 'MANAGER'),
(18, 'manager08', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'manager.08@example.com', '0912222228', 'MANAGER'),
(19, 'manager09', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'manager.09@example.com', '0912222229', 'MANAGER'),
(20, 'manager10', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'manager.10@example.com', '0912222230', 'MANAGER');

-- STAFF Users (10 users) - ID từ 21 đến 30
INSERT INTO tbl_user (id, username, password, email, phone, role) VALUES
(21, 'staff01', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'staff.01@example.com', '0923333331', 'STAFF'),
(22, 'staff02', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'staff.02@example.com', '0923333332', 'STAFF'),
(23, 'staff03', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'staff.03@example.com', '0923333333', 'STAFF'),
(24, 'staff04', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'staff.04@example.com', '0923333334', 'STAFF'),
(25, 'staff05', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'staff.05@example.com', '0923333335', 'STAFF'),
(26, 'staff06', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'staff.06@example.com', '0923333336', 'STAFF'),
(27, 'staff07', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'staff.07@example.com', '0923333337', 'STAFF'),
(28, 'staff08', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'staff.08@example.com', '0923333338', 'STAFF'),
(29, 'staff09', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'staff.09@example.com', '0923333339', 'STAFF'),
(30, 'staff10', '$2a$10$5zVt.fbYLgqdw9Rn3.coX.xETazDmzblSKgPJtG71yUCHYWpnDoqW', 'staff.10@example.com', '0923333340', 'STAFF');

-- Dữ liệu mẫu cho bảng tbl_category (20 danh mục)
INSERT INTO tbl_category (id, name, description) VALUES
(1, 'Electronics', 'Thiết bị điện tử, điện gia dụng như điện thoại, laptop, TV.'),
(2, 'Home Appliances', 'Đồ dùng và thiết bị gia đình như máy giặt, tủ lạnh, bếp.'),
(3, 'Books', 'Các loại sách, tạp chí, truyện tranh.'),
(4, 'Sporting Goods', 'Dụng cụ và thiết bị thể thao, quần áo thể thao.'),
(5, 'Beauty Products', 'Sản phẩm chăm sóc da, trang điểm, chăm sóc cá nhân.'),
(6, 'Fashion - Men', 'Quần áo, giày dép, phụ kiện thời trang dành cho nam giới.'),
(7, 'Fashion - Women', 'Quần áo, giày dép, phụ kiện thời trang dành cho nữ giới.'),
(8, 'Furniture', 'Nội thất cho nhà ở và văn phòng như bàn, ghế, tủ.'),
(9, 'Garden & Outdoor', 'Vật dụng làm vườn, đồ trang trí ngoài trời, thiết bị cắm trại.'),
(10, 'Toys & Games', 'Đồ chơi cho trẻ em, trò chơi board game, video game.'),
(11, 'Pet Supplies', 'Thức ăn, phụ kiện, đồ dùng cho thú cưng.'),
(12, 'Automotive Parts', 'Phụ tùng, linh kiện, phụ kiện cho ô tô và xe máy.'),
(13, 'Office Supplies', 'Văn phòng phẩm, thiết bị văn phòng, giấy tờ.'),
(14, 'Groceries - Dry Goods', 'Thực phẩm khô, đồ hộp, gia vị, đồ ăn vặt.'),
(15, 'Groceries - Fresh', 'Thực phẩm tươi sống như rau củ quả, thịt, cá.'),
(16, 'Tools & Home Improvement', 'Dụng cụ sửa chữa, vật liệu xây dựng, thiết bị bảo trì nhà cửa.'),
(17, 'Baby Products', 'Sản phẩm dành cho trẻ sơ sinh và trẻ nhỏ như tã, sữa, quần áo.'),
(18, 'Health & Wellness', 'Thực phẩm chức năng, vitamin, dụng cụ y tế cá nhân.'),
(19, 'Art & Craft Supplies', 'Vật liệu, dụng cụ cho các hoạt động nghệ thuật và thủ công.'),
(20, 'Jewelry', 'Trang sức, đồng hồ, phụ kiện thời trang cao cấp.');

-- Dữ liệu mẫu cho bảng tbl_warehouse (10 kho hàng)
INSERT INTO tbl_warehouse (id, name, location) VALUES
(1, 'Kho Trung Tâm Sài Gòn', 'Số 1, Đường Nguyễn Văn Linh, Quận 7, TP. Hồ Chí Minh'),
(2, 'Kho Miền Bắc', 'Khu Công Nghiệp Thăng Long, Đông Anh, Hà Nội'),
(3, 'Kho Đà Nẵng', 'Đường Nguyễn Tất Thành, Quận Liên Chiểu, Đà Nẵng'),
(4, 'Kho Cần Thơ', 'Khu Công Nghiệp Trà Nóc, Quận Bình Thủy, Cần Thơ'),
(5, 'Kho Hàng Bình Dương', 'Đại lộ Bình Dương, TP. Thuận An, Bình Dương'),
(6, 'Kho Hải Phòng', 'Đường Đình Vũ, Quận Hải An, Hải Phòng'),
(7, 'Kho Long An', 'Khu Công Nghiệp Đức Hòa I, Huyện Đức Hòa, Long An'),
(8, 'Kho Đồng Nai', 'Đường 25B, Khu Công Nghiệp Nhơn Trạch 3, Đồng Nai'),
(9, 'Kho Vinh Phúc', 'Khu Công Nghiệp Khai Quang, TP. Vĩnh Yên, Vĩnh Phúc'),
(10, 'Kho Quảng Ninh', 'Khu Công Nghiệp Cái Lân, TP. Hạ Long, Quảng Ninh');

-- Dữ liệu mẫu cho bảng tbl_inventory (2 inventory cho mỗi warehouse)

-- Warehouse 1 (ID: 1)
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (1, 1);
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (2, 1);

-- Warehouse 2 (ID: 2)
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (3, 2);
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (4, 2);

-- Warehouse 3 (ID: 3)
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (5, 3);
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (6, 3);

-- Warehouse 4 (ID: 4)
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (7, 4);
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (8, 4);

-- Warehouse 5 (ID: 5)
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (9, 5);
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (10, 5);

-- Warehouse 6 (ID: 6)
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (11, 6);
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (12, 6);

-- Warehouse 7 (ID: 7)
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (13, 7);
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (14, 7);

-- Warehouse 8 (ID: 8)
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (15, 8);
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (16, 8);

-- Warehouse 9 (ID: 9)
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (17, 9);
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (18, 9);

-- Warehouse 10 (ID: 10)
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (19, 10);
INSERT INTO tbl_inventory (id, warehouse_id) VALUES (20, 10);

-- Dữ liệu mẫu cho bảng tbl_product (120 sản phẩm)

-- Product Group 1 (Category 1: Electronics, Inventory 1-6)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(1, '2023-11-20 09:30:00', NULL, 'A-01-01', 'Smartphone X1', 150, 799.99, 1, 1),
(2, '2023-11-21 10:00:00', NULL, 'A-01-02', 'Laptop Pro 15', 80, 1250.00, 1, 2),
(3, '2023-11-22 11:15:00', NULL, 'A-01-03', 'Smart TV 55 Inch', 50, 899.50, 1, 3),
(4, '2023-11-23 14:45:00', NULL, 'A-01-04', 'Wireless Earbuds X', 300, 129.99, 1, 4),
(5, '2023-11-24 09:00:00', NULL, 'A-01-05', 'Smartwatch Series 5', 200, 299.00, 1, 5),
(6, '2023-11-25 13:20:00', NULL, 'A-01-06', 'Gaming Console Pro', 70, 499.99, 1, 6);

-- Product Group 2 (Category 2: Home Appliances, Inventory 7-12)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(7, '2023-11-18 10:00:00', NULL, 'B-02-01', 'Máy giặt cửa ngang', 40, 550.00, 2, 7),
(8, '2023-11-19 11:30:00', NULL, 'B-02-02', 'Tủ lạnh Inverter', 30, 780.00, 2, 8),
(9, '2023-11-20 12:40:00', NULL, 'B-02-03', 'Máy hút bụi không dây', 90, 180.00, 2, 9),
(10, '2023-11-21 15:00:00', NULL, 'B-02-04', 'Nồi chiên không dầu XL', 120, 95.00, 2, 10),
(11, '2023-11-22 08:30:00', NULL, 'B-02-05', 'Bếp từ đôi cao cấp', 60, 320.00, 2, 11),
(12, '2023-11-23 10:10:00', NULL, 'B-02-06', 'Máy lọc không khí', 75, 210.00, 2, 12);

-- Product Group 3 (Category 3: Books, Inventory 13-18)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(13, '2023-12-01 14:00:00', NULL, 'C-03-01', 'Sách "Cuộc đời phi thường"', 250, 15.00, 3, 13),
(14, '2023-12-02 09:00:00', NULL, 'C-03-02', 'Tiểu thuyết "Hành trình mới"', 300, 12.50, 3, 14),
(15, '2023-12-03 10:30:00', NULL, 'C-03-03', 'Truyện tranh "Anh hùng vũ trụ"', 400, 8.00, 3, 15),
(16, '2023-12-04 11:45:00', NULL, 'C-03-04', 'Tạp chí Khoa học', 180, 5.00, 3, 16),
(17, '2023-12-05 13:00:00', NULL, 'C-03-05', 'Sách dạy nấu ăn', 220, 20.00, 3, 17),
(18, '2023-12-06 14:15:00', NULL, 'C-03-06', 'Từ điển Anh-Việt', 100, 25.00, 3, 18);

-- Product Group 4 (Category 4: Sporting Goods, Inventory 19-20, and then 1-4 again for consistency)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(19, '2023-11-28 09:00:00', NULL, 'D-04-01', 'Bộ gậy golf cao cấp', 20, 800.00, 4, 19),
(20, '2023-11-29 10:00:00', NULL, 'D-04-02', 'Xe đạp địa hình', 35, 350.00, 4, 20),
(21, '2023-11-30 11:00:00', NULL, 'D-04-03', 'Quả bóng đá tiêu chuẩn', 500, 25.00, 4, 1),
(22, '2023-12-01 12:00:00', NULL, 'D-04-04', 'Vợt cầu lông Carbon', 150, 45.00, 4, 2),
(23, '2023-12-02 13:00:00', NULL, 'D-04-05', 'Thảm tập Yoga', 280, 18.00, 4, 3),
(24, '2023-12-03 14:00:00', NULL, 'D-04-06', 'Giày chạy bộ (Nam, size 42)', 100, 75.00, 4, 4);

-- Product Group 5 (Category 5: Beauty Products, Inventory 5-10)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(25, '2023-12-05 09:00:00', '2025-06-01 00:00:00', 'E-05-01', 'Kem dưỡng ẩm da mặt', 300, 35.00, 5, 5),
(26, '2023-12-06 10:00:00', '2025-03-15 00:00:00', 'E-05-02', 'Sữa rửa mặt tạo bọt', 400, 20.00, 5, 6),
(27, '2023-12-07 11:00:00', '2024-12-31 00:00:00', 'E-05-03', 'Son môi lì màu đỏ', 250, 18.00, 5, 7),
(28, '2023-12-08 12:00:00', '2026-01-01 00:00:00', 'E-05-04', 'Dầu gội thảo dược', 350, 12.00, 5, 8),
(29, '2023-12-09 13:00:00', '2025-09-30 00:00:00', 'E-05-05', 'Nước hoa hồng cân bằng da', 200, 28.00, 5, 9),
(30, '2023-12-10 14:00:00', '2025-07-20 00:00:00', 'E-05-06', 'Mặt nạ giấy dưỡng ẩm', 500, 5.00, 5, 10);

-- Product Group 6 (Category 6: Fashion - Men, Inventory 11-16)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(31, '2023-11-20 09:00:00', NULL, 'F-06-01', 'Áo sơ mi nam công sở (Trắng, L)', 100, 45.00, 6, 11),
(32, '2023-11-21 10:00:00', NULL, 'F-06-02', 'Quần Jeans Skinny (Xanh, size 32)', 120, 55.00, 6, 12),
(33, '2023-11-22 11:00:00', NULL, 'F-06-03', 'Giày da nam cổ điển (Đen, size 41)', 80, 80.00, 6, 13),
(34, '2023-11-23 12:00:00', NULL, 'F-06-04', 'Áo khoác dù nam (Đen, XL)', 70, 65.00, 6, 14),
(35, '2023-11-24 13:00:00', NULL, 'F-06-05', 'Thắt lưng da nam', 150, 30.00, 6, 15),
(36, '2023-11-25 14:00:00', NULL, 'F-06-06', 'Ví da nam cao cấp', 90, 40.00, 6, 16);

-- Product Group 7 (Category 7: Fashion - Women, Inventory 17-20, then 1-2)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(37, '2023-12-01 09:00:00', NULL, 'G-07-01', 'Váy maxi họa tiết hoa (M)', 180, 60.00, 7, 17),
(38, '2023-12-02 10:00:00', NULL, 'G-07-02', 'Áo len dệt kim nữ (S)', 200, 38.00, 7, 18),
(39, '2023-12-03 11:00:00', NULL, 'G-07-03', 'Giày cao gót (Nude, size 37)', 100, 70.00, 7, 19),
(40, '2023-12-04 12:00:00', NULL, 'G-07-04', 'Túi xách đeo chéo nữ', 130, 90.00, 7, 20),
(41, '2023-12-05 13:00:00', NULL, 'G-07-05', 'Quần Legging thể thao nữ (L)', 250, 25.00, 7, 1),
(42, '2023-12-06 14:00:00', NULL, 'G-07-06', 'Khăn lụa vuông', 160, 20.00, 7, 2);

-- Product Group 8 (Category 8: Furniture, Inventory 3-8)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(43, '2023-11-15 09:00:00', NULL, 'H-08-01', 'Ghế sofa 3 chỗ màu xám', 15, 450.00, 8, 3),
(44, '2023-11-16 10:00:00', NULL, 'H-08-02', 'Bàn ăn gỗ sồi 6 ghế', 10, 600.00, 8, 4),
(45, '2023-11-17 11:00:00', NULL, 'H-08-03', 'Tủ quần áo 3 cánh', 20, 300.00, 8, 5),
(46, '2023-11-18 12:00:00', NULL, 'H-08-04', 'Kệ sách đa năng', 40, 120.00, 8, 6),
(47, '2023-11-19 13:00:00', NULL, 'H-08-05', 'Giường ngủ cỡ Queen', 25, 520.00, 8, 7),
(48, '2023-11-20 14:00:00', NULL, 'H-08-06', 'Bàn làm việc L-shape', 30, 180.00, 8, 8);

-- Product Group 9 (Category 9: Garden & Outdoor, Inventory 9-14)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(49, '2023-12-01 09:00:00', NULL, 'I-09-01', 'Bộ bàn ghế ngoài trời 4 chỗ', 50, 280.00, 9, 9),
(50, '2023-12-02 10:00:00', NULL, 'I-09-02', 'Ô che nắng sân vườn', 70, 90.00, 9, 10),
(51, '2023-12-03 11:00:00', NULL, 'I-09-03', 'Máy cắt cỏ chạy điện', 30, 150.00, 9, 11),
(52, '2023-12-04 12:00:00', NULL, 'I-09-04', 'Đèn năng lượng mặt trời sân vườn', 200, 15.00, 9, 12),
(53, '2023-12-05 13:00:00', NULL, 'I-09-05', 'Bộ dụng cụ làm vườn', 180, 40.00, 9, 13),
(54, '2023-12-06 14:00:00', NULL, 'I-09-06', 'Ghế gấp dã ngoại', 100, 25.00, 9, 14);

-- Product Group 10 (Category 10: Toys & Games, Inventory 15-20)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(55, '2023-11-25 09:00:00', NULL, 'J-10-01', 'Bộ xếp hình Lego Castle', 120, 75.00, 10, 15),
(56, '2023-11-26 10:00:00', NULL, 'J-10-02', 'Búp bê công chúa Elsa', 180, 25.00, 10, 16),
(57, '2023-11-27 11:00:00', NULL, 'J-10-03', 'Xe điều khiển từ xa', 90, 40.00, 10, 17),
(58, '2023-11-28 12:00:00', NULL, 'J-10-04', 'Bộ cờ tỷ phú gia đình', 150, 30.00, 10, 18),
(59, '2023-11-29 13:00:00', NULL, 'J-10-05', 'Đồ chơi nấu ăn cho bé', 200, 20.00, 10, 19),
(60, '2023-11-30 14:00:00', NULL, 'J-10-06', 'Bộ đồ chơi bác sĩ', 160, 22.00, 10, 20);

-- Product Group 11 (Category 11: Pet Supplies, Inventory 1-6)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(61, '2023-12-01 09:00:00', '2024-06-30 00:00:00', 'K-11-01', 'Thức ăn chó lớn (10kg)', 150, 35.00, 11, 1),
(62, '2023-12-02 10:00:00', '2024-05-15 00:00:00', 'K-11-02', 'Cát vệ sinh mèo (5kg)', 200, 10.00, 11, 2),
(63, '2023-12-03 11:00:00', NULL, 'K-11-03', 'Đồ chơi cắn gặm cho chó', 250, 8.00, 11, 3),
(64, '2023-12-04 12:00:00', NULL, 'K-11-04', 'Vòng cổ cho chó', 300, 12.00, 11, 4),
(65, '2023-12-05 13:00:00', '2024-08-31 00:00:00', 'K-11-05', 'Pate cho mèo (hộp)', 400, 3.50, 11, 5),
(66, '2023-12-06 14:00:00', NULL, 'K-11-06', 'Lồng vận chuyển thú cưng', 80, 50.00, 11, 6);

-- Product Group 12 (Category 12: Automotive Parts, Inventory 7-12)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(67, '2023-11-20 09:00:00', NULL, 'L-12-01', 'Lốp xe du lịch (size 17)', 50, 120.00, 12, 7),
(68, '2023-11-21 10:00:00', NULL, 'L-12-02', 'Dầu động cơ tổng hợp (4L)', 100, 45.00, 12, 8),
(69, '2023-11-22 11:00:00', NULL, 'L-12-03', 'Ắc quy ô tô', 70, 80.00, 12, 9),
(70, '2023-11-23 12:00:00', NULL, 'L-12-04', 'Đèn pha LED ô tô', 120, 60.00, 12, 10),
(71, '2023-11-24 13:00:00', NULL, 'L-12-05', 'Gạt mưa đa năng', 200, 15.00, 12, 11),
(72, '2023-11-25 14:00:00', NULL, 'L-12-06', 'Bọc vô lăng da', 150, 20.00, 12, 12);

-- Product Group 13 (Category 13: Office Supplies, Inventory 13-18)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(73, '2023-12-01 09:00:00', NULL, 'M-13-01', 'Giấy A4 (ream)', 500, 5.00, 13, 13),
(74, '2023-12-02 10:00:00', NULL, 'M-13-02', 'Bút bi xanh (hộp 10 cây)', 300, 8.00, 13, 14),
(75, '2023-12-03 11:00:00', NULL, 'M-13-03', 'Sổ tay A5 bìa cứng', 250, 7.00, 13, 15),
(76, '2023-12-04 12:00:00', NULL, 'M-13-04', 'Kẹp giấy các loại (hộp)', 400, 4.00, 13, 16),
(77, '2023-12-05 13:00:00', NULL, 'M-13-05', 'Băng dính trong (cuộn)', 350, 3.00, 13, 17),
(78, '2023-12-06 14:00:00', NULL, 'M-13-06', 'Máy tính cầm tay', 100, 15.00, 13, 18);

-- Product Group 14 (Category 14: Groceries - Dry Goods, Inventory 19-20, then 1-4)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(79, '2023-12-10 09:00:00', '2025-06-30 00:00:00', 'N-14-01', 'Gạo Jasmine (5kg)', 200, 8.00, 14, 19),
(80, '2023-12-11 10:00:00', '2025-03-31 00:00:00', 'N-14-02', 'Mì gói Hảo Hảo (thùng)', 150, 12.00, 14, 20),
(81, '2023-12-12 11:00:00', '2024-11-30 00:00:00', 'N-14-03', 'Dầu ăn thực vật (2L)', 180, 6.00, 14, 1),
(82, '2023-12-13 12:00:00', '2025-01-31 00:00:00', 'N-14-04', 'Đường tinh luyện (1kg)', 300, 2.50, 14, 2),
(83, '2023-12-14 13:00:00', '2025-09-30 00:00:00', 'N-14-05', 'Cà phê hòa tan (hộp)', 250, 9.00, 14, 3),
(84, '2023-12-15 14:00:00', '2024-08-31 00:00:00', 'N-14-06', 'Bánh quy socola (gói)', 350, 4.00, 14, 4);

-- Product Group 15 (Category 15: Groceries - Fresh, Inventory 5-10)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(85, '2024-01-05 08:00:00', '2024-01-12 00:00:00', 'O-15-01', 'Táo Gala (kg)', 100, 3.00, 15, 5),
(86, '2024-01-06 08:30:00', '2024-01-10 00:00:00', 'O-15-02', 'Cá hồi tươi (kg)', 50, 20.00, 15, 6),
(87, '2024-01-07 09:00:00', '2024-01-14 00:00:00', 'O-15-03', 'Thịt bò tươi (kg)', 60, 18.00, 15, 7),
(88, '2024-01-08 09:30:00', '2024-01-11 00:00:00', 'O-15-04', 'Sữa tươi không đường (1L)', 200, 2.00, 15, 8),
(89, '2024-01-09 10:00:00', '2024-01-13 00:00:00', 'O-15-05', 'Rau xà lách (kg)', 150, 1.50, 15, 9),
(90, '2024-01-10 10:30:00', '2024-01-15 00:00:00', 'O-15-06', 'Trứng gà tươi (chục)', 120, 2.80, 15, 10);

-- Product Group 16 (Category 16: Tools & Home Improvement, Inventory 11-16)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(91, '2023-11-25 09:00:00', NULL, 'P-16-01', 'Bộ dụng cụ cầm tay 50 chi tiết', 80, 60.00, 16, 11),
(92, '2023-11-26 10:00:00', NULL, 'P-16-02', 'Máy khoan điện cầm tay', 50, 75.00, 16, 12),
(93, '2023-11-27 11:00:00', NULL, 'P-16-03', 'Bộ cờ lê đa năng', 100, 40.00, 16, 13),
(94, '2023-11-28 12:00:00', NULL, 'P-16-04', 'Thang nhôm rút gọn', 30, 90.00, 16, 14),
(95, '2023-11-29 13:00:00', NULL, 'P-16-05', 'Hộp vít các loại', 150, 18.00, 16, 15),
(96, '2023-11-30 14:00:00', NULL, 'P-16-06', 'Dây điện cuộn (50m)', 200, 22.00, 16, 16);

-- Product Group 17 (Category 17: Baby Products, Inventory 17-20, then 1-2)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(97, '2023-12-05 09:00:00', '2025-01-31 00:00:00', 'Q-17-01', 'Tã giấy trẻ em (size M, gói lớn)', 300, 25.00, 17, 17),
(98, '2023-12-06 10:00:00', '2024-09-30 00:00:00', 'Q-17-02', 'Sữa công thức cho bé (hộp 900g)', 150, 30.00, 17, 18),
(99, '2023-12-07 11:00:00', NULL, 'Q-17-03', 'Bình sữa PPSU (240ml)', 200, 12.00, 17, 19),
(100, '2023-12-08 12:00:00', NULL, 'Q-17-04', 'Khăn ướt không mùi (gói)', 400, 4.00, 17, 20),
(101, '2023-12-09 13:00:00', NULL, 'Q-17-05', 'Quần áo sơ sinh (bộ 5 món)', 250, 20.00, 17, 1),
(102, '2023-12-10 14:00:00', NULL, 'Q-17-06', 'Đồ chơi gặm nướu silicone', 300, 7.00, 17, 2);

-- Product Group 18 (Category 18: Health & Wellness, Inventory 3-8)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(103, '2023-12-01 09:00:00', '2024-10-31 00:00:00', 'R-18-01', 'Vitamin tổng hợp (lọ 60 viên)', 180, 22.00, 18, 3),
(104, '2023-12-02 10:00:00', '2025-02-28 00:00:00', 'R-18-02', 'Dầu cá Omega-3 (lọ 90 viên)', 120, 35.00, 18, 4),
(105, '2023-12-03 11:00:00', NULL, 'R-18-03', 'Máy đo huyết áp điện tử', 70, 50.00, 18, 5),
(106, '2023-12-04 12:00:00', NULL, 'R-18-04', 'Nhiệt kế hồng ngoại', 90, 28.00, 18, 6),
(107, '2023-12-05 13:00:00', '2024-07-31 00:00:00', 'R-18-05', 'Khẩu trang y tế (hộp 50 cái)', 500, 5.00, 18, 7),
(108, '2023-12-06 14:00:00', '2025-04-30 00:00:00', 'R-18-06', 'Băng gạc y tế tiệt trùng (gói)', 300, 3.00, 18, 8);

-- Product Group 19 (Category 19: Art & Craft Supplies, Inventory 9-14)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(109, '2023-12-01 09:00:00', NULL, 'S-19-01', 'Bộ màu nước 24 màu', 200, 18.00, 19, 9),
(110, '2023-12-02 10:00:00', NULL, 'S-19-02', 'Giấy vẽ A3 (tệp 100 tờ)', 150, 10.00, 19, 10),
(111, '2023-12-03 11:00:00', NULL, 'S-19-03', 'Bộ cọ vẽ acrylic', 100, 15.00, 19, 11),
(112, '2023-12-04 12:00:00', NULL, 'S-19-04', 'Đất sét tự khô (500g)', 250, 8.00, 19, 12),
(113, '2023-12-05 13:00:00', NULL, 'S-19-05', 'Keo dán đa năng', 300, 5.00, 19, 13),
(114, '2023-12-06 14:00:00', NULL, 'S-19-06', 'Kéo thủ công sắc bén', 180, 7.00, 19, 14);

-- Product Group 20 (Category 20: Jewelry, Inventory 15-20)
INSERT INTO tbl_product (id, entry_date, expiry_date, location, name, quantity, unit_price, category_id, inventory_id) VALUES
(115, '2023-11-20 09:00:00', NULL, 'T-20-01', 'Vòng cổ bạc đính đá', 50, 95.00, 20, 15),
(116, '2023-11-21 10:00:00', NULL, 'T-20-02', 'Bông tai ngọc trai tự nhiên', 70, 70.00, 20, 16),
(117, '2023-11-22 11:00:00', NULL, 'T-20-03', 'Nhẫn vàng 18K đính kim cương', 20, 1200.00, 20, 17),
(118, '2023-11-23 12:00:00', NULL, 'T-20-04', 'Lắc tay charm bạc', 80, 55.00, 20, 18),
(119, '2023-11-24 13:00:00', NULL, 'T-20-05', 'Đồng hồ đeo tay nữ sang trọng', 40, 250.00, 20, 19),
(120, '2023-11-25 14:00:00', NULL, 'T-20-06', 'Bộ trang sức cưới', 10, 1500.00, 20, 20);

-- Dữ liệu mẫu cho bảng tbl_supplier (20 nhà cung cấp)

-- Suppliers for Warehouse 1 (ID: 1) - Kho Trung Tâm Sài Gòn
INSERT INTO tbl_supplier (id, name, address, contact_number, warehouse_id) VALUES
(1, 'Công Ty Cung Ứng Toàn Cầu Sài Gòn', '200 Lê Lợi, Quận 1, TP.HCM', '0901234567', 1),
(2, 'Nhà Phân Phối Điện Tử Miền Nam', '150 Phan Văn Trị, Gò Vấp, TP.HCM', '0902345678', 1);

-- Suppliers for Warehouse 2 (ID: 2) - Kho Miền Bắc
INSERT INTO tbl_supplier (id, name, address, contact_number, warehouse_id) VALUES
(3, 'Thực Phẩm Sạch Vườn Xanh Hà Nội', 'Khu Công Nghiệp Thăng Long, Đông Anh, Hà Nội', '0903456789', 2),
(4, 'Vật Tư Văn Phòng Miền Bắc', 'Ngõ 200, Đường Giải Phóng, Hà Nội', '0904567890', 2);

-- Suppliers for Warehouse 3 (ID: 3) - Kho Đà Nẵng
INSERT INTO tbl_supplier (id, name, address, contact_number, warehouse_id) VALUES
(5, 'Đồ Gia Dụng Tiện Ích Miền Trung', 'Đường 3 tháng 2, Quận Hải Châu, Đà Nẵng', '0905678901', 3),
(6, 'Thời Trang Nữ Cao Cấp Đà Nẵng', '250 Nguyễn Tất Thành, Quận Thanh Khê, Đà Nẵng', '0906789012', 3);

-- Suppliers for Warehouse 4 (ID: 4) - Kho Cần Thơ
INSERT INTO tbl_supplier (id, name, address, contact_number, warehouse_id) VALUES
(7, 'Sản Phẩm Thể Thao Đồng Bằng', 'Khu Công Nghiệp Trà Nóc, Quận Bình Thủy, Cần Thơ', '0907890123', 4),
(8, 'Sách Giáo Dục Miền Tây', '180 Đường 30/4, Quận Ninh Kiều, Cần Thơ', '0908901234', 4);

-- Suppliers for Warehouse 5 (ID: 5) - Kho Hàng Bình Dương
INSERT INTO tbl_supplier (id, name, address, contact_number, warehouse_id) VALUES
(9, 'Nội Thất Hiện Đại Bình Dương', 'Đại lộ Bình Dương, TP. Thuận An, Bình Dương', '0909012345', 5),
(10, 'Đồ Chơi Trẻ Em Toàn Quốc', 'Khu Dân Cư Việt-Sing, TP. Thuận An, Bình Dương', '0910123456', 5);

-- Suppliers for Warehouse 6 (ID: 6) - Kho Hải Phòng
INSERT INTO tbl_supplier (id, name, address, contact_number, warehouse_id) VALUES
(11, 'Thiết Bị Công Nghiệp Hải Phòng', 'Đường Đình Vũ, Quận Hải An, Hải Phòng', '0911234567', 6),
(12, 'Phụ Tùng Ô Tô Miền Duy Hải', '200 Lạch Tray, Quận Ngô Quyền, Hải Phòng', '0912345678', 6);

-- Suppliers for Warehouse 7 (ID: 7) - Kho Long An
INSERT INTO tbl_supplier (id, name, address, contact_number, warehouse_id) VALUES
(13, 'Hàng Tiêu Dùng Nhanh Long An', 'Khu Công Nghiệp Đức Hòa I, Huyện Đức Hòa, Long An', '0913456789', 7),
(14, 'Dược Phẩm & Y Tế Phương Nam', 'Đường Hùng Vương, TP. Tân An, Long An', '0914567890', 7);

-- Suppliers for Warehouse 8 (ID: 8) - Kho Đồng Nai
INSERT INTO tbl_supplier (id, name, address, contact_number, warehouse_id) VALUES
(15, 'Vật Liệu Xây Dựng Miền Đông', 'Đường 25B, KCN Nhơn Trạch 3, Đồng Nai', '0915678901', 8),
(16, 'Sản Phẩm Vườn Cây Đồng Nai', 'Khu Công Nghiệp Amata, TP. Biên Hòa, Đồng Nai', '0916789012', 8);

-- Suppliers for Warehouse 9 (ID: 9) - Kho Vinh Phúc
INSERT INTO tbl_supplier (id, name, address, contact_number, warehouse_id) VALUES
(17, 'Mỹ Phẩm Làm Đẹp Miền Bắc', 'Khu Công Nghiệp Khai Quang, TP. Vĩnh Yên, Vĩnh Phúc', '0917890123', 9),
(18, 'Đồ Dùng Thú Cưng Chất Lượng', 'Đường Nguyễn Tất Thành, TP. Vĩnh Yên, Vĩnh Phúc', '0918901234', 9);

-- Suppliers for Warehouse 10 (ID: 10) - Kho Quảng Ninh
INSERT INTO tbl_supplier (id, name, address, contact_number, warehouse_id) VALUES
(19, 'Trang Sức Đá Quý Cao Cấp', 'Khu Công Nghiệp Cái Lân, TP. Hạ Long, Quảng Ninh', '0919012345', 10),
(20, 'Thực Phẩm Tươi Sống Vùng Biển', 'Bến Đoan, TP. Hạ Long, Quảng Ninh', '0920123456', 10);

-- Dữ liệu mẫu cho bảng tbl_product_supplier (120 bản ghi)

-- ProductSupplier for Product IDs 1-6 (Category: Electronics, Supplied by Supplier 1-6)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(1, 1, 1, '2023-11-10', 200, 700.00), -- Smartphone X1
(2, 2, 2, '2023-11-12', 100, 1100.00), -- Laptop Pro 15
(3, 3, 3, '2023-11-14', 60, 800.00),  -- Smart TV 55 Inch
(4, 4, 4, '2023-11-16', 350, 110.00), -- Wireless Earbuds X
(5, 5, 5, '2023-11-18', 250, 260.00), -- Smartwatch Series 5
(6, 6, 6, '2023-11-19', 90, 450.00);  -- Gaming Console Pro

-- ProductSupplier for Product IDs 7-12 (Category: Home Appliances, Supplied by Supplier 7-12)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(7, 7, 7, '2023-11-08', 50, 500.00),  -- Máy giặt cửa ngang
(8, 8, 8, '2023-11-09', 40, 700.00),  -- Tủ lạnh Inverter
(9, 9, 9, '2023-11-10', 100, 160.00), -- Máy hút bụi không dây
(10, 10, 10, '2023-11-11', 150, 85.00), -- Nồi chiên không dầu XL
(11, 11, 11, '2023-11-12', 70, 290.00), -- Bếp từ đôi cao cấp
(12, 12, 12, '2023-11-13', 90, 190.00); -- Máy lọc không khí

-- ProductSupplier for Product IDs 13-18 (Category: Books, Supplied by Supplier 13-18)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(13, 13, 13, '2023-11-20', 300, 10.00), -- Sách "Cuộc đời phi thường"
(14, 14, 14, '2023-11-21', 400, 9.00),  -- Tiểu thuyết "Hành trình mới"
(15, 15, 15, '2023-11-22', 500, 6.00),  -- Truyện tranh "Anh hùng vũ trụ"
(16, 16, 16, '2023-11-23', 200, 4.00),  -- Tạp chí Khoa học
(17, 17, 17, '2023-11-24', 280, 15.00), -- Sách dạy nấu ăn
(18, 18, 18, '2023-11-25', 120, 20.00); -- Từ điển Anh-Việt

-- ProductSupplier for Product IDs 19-24 (Category: Sporting Goods, Supplied by Supplier 19-20, then 1-4)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(19, 19, 19, '2023-11-15', 30, 700.00),  -- Bộ gậy golf cao cấp
(20, 20, 20, '2023-11-16', 45, 300.00),  -- Xe đạp địa hình
(21, 21, 1, '2023-11-17', 600, 20.00),  -- Quả bóng đá tiêu chuẩn
(22, 22, 2, '2023-11-18', 200, 40.00),  -- Vợt cầu lông Carbon
(23, 23, 3, '2023-11-19', 350, 15.00),  -- Thảm tập Yoga
(24, 24, 4, '2023-11-20', 120, 65.00);  -- Giày chạy bộ (Nam, size 42)

-- ProductSupplier for Product IDs 25-30 (Category: Beauty Products, Supplied by Supplier 5-10)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(25, 25, 5, '2023-11-25', 400, 30.00), -- Kem dưỡng ẩm da mặt
(26, 26, 6, '2023-11-26', 500, 17.00), -- Sữa rửa mặt tạo bọt
(27, 27, 7, '2023-11-27', 300, 15.00), -- Son môi lì màu đỏ
(28, 28, 8, '2023-11-28', 450, 10.00), -- Dầu gội thảo dược
(29, 29, 9, '2023-11-29', 280, 25.00), -- Nước hoa hồng cân bằng da
(30, 30, 10, '2023-11-30', 600, 4.00);  -- Mặt nạ giấy dưỡng ẩm

-- ProductSupplier for Product IDs 31-36 (Category: Fashion - Men, Supplied by Supplier 11-16)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(31, 31, 11, '2023-11-05', 120, 40.00), -- Áo sơ mi nam công sở (Trắng, L)
(32, 32, 12, '2023-11-06', 150, 50.00), -- Quần Jeans Skinny (Xanh, size 32)
(33, 33, 13, '2023-11-07', 100, 70.00), -- Giày da nam cổ điển (Đen, size 41)
(34, 34, 14, '2023-11-08', 90, 58.00),  -- Áo khoác dù nam (Đen, XL)
(35, 35, 15, '2023-11-09', 200, 27.00), -- Thắt lưng da nam
(36, 36, 16, '2023-11-10', 110, 35.00); -- Ví da nam cao cấp

-- ProductSupplier for Product IDs 37-42 (Category: Fashion - Women, Supplied by Supplier 17-20, then 1-2)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(37, 37, 17, '2023-11-20', 200, 55.00), -- Váy maxi họa tiết hoa (M)
(38, 38, 18, '2023-11-21', 250, 34.00), -- Áo len dệt kim nữ (S)
(39, 39, 19, '2023-11-22', 120, 62.00), -- Giày cao gót (Nude, size 37)
(40, 40, 20, '2023-11-23', 150, 80.00), -- Túi xách đeo chéo nữ
(41, 41, 1, '2023-11-24', 300, 22.00), -- Quần Legging thể thao nữ (L)
(42, 42, 2, '2023-11-25', 200, 18.00); -- Khăn lụa vuông

-- ProductSupplier for Product IDs 43-48 (Category: Furniture, Supplied by Supplier 3-8)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(43, 43, 3, '2023-10-30', 20, 400.00),  -- Ghế sofa 3 chỗ màu xám
(44, 44, 4, '2023-10-31', 12, 550.00),  -- Bàn ăn gỗ sồi 6 ghế
(45, 45, 5, '2023-11-01', 25, 270.00),  -- Tủ quần áo 3 cánh
(46, 46, 6, '2023-11-02', 50, 100.00),  -- Kệ sách đa năng
(47, 47, 7, '2023-11-03', 30, 480.00),  -- Giường ngủ cỡ Queen
(48, 48, 8, '2023-11-04', 40, 160.00);  -- Bàn làm việc L-shape

-- ProductSupplier for Product IDs 49-54 (Category: Garden & Outdoor, Supplied by Supplier 9-14)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(49, 49, 9, '2023-11-20', 60, 250.00),  -- Bộ bàn ghế ngoài trời 4 chỗ
(50, 50, 10, '2023-11-21', 80, 80.00),  -- Ô che nắng sân vườn
(51, 51, 11, '2023-11-22', 40, 130.00), -- Máy cắt cỏ chạy điện
(52, 52, 12, '2023-11-23', 250, 12.00), -- Đèn năng lượng mặt trời sân vườn
(53, 53, 13, '2023-11-24', 200, 35.00), -- Bộ dụng cụ làm vườn
(54, 54, 14, '2023-11-25', 120, 20.00); -- Ghế gấp dã ngoại

-- ProductSupplier for Product IDs 55-60 (Category: Toys & Games, Supplied by Supplier 15-20)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(55, 55, 15, '2023-11-15', 150, 65.00), -- Bộ xếp hình Lego Castle
(56, 56, 16, '2023-11-16', 200, 20.00), -- Búp bê công chúa Elsa
(57, 57, 17, '2023-11-17', 100, 35.00), -- Xe điều khiển từ xa
(58, 58, 18, '2023-11-18', 180, 25.00), -- Bộ cờ tỷ phú gia đình
(59, 59, 19, '2023-11-19', 250, 17.00), -- Đồ chơi nấu ăn cho bé
(60, 60, 20, '2023-11-20', 200, 19.00); -- Bộ đồ chơi bác sĩ

-- ProductSupplier for Product IDs 61-66 (Category: Pet Supplies, Supplied by Supplier 1-6)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(61, 61, 1, '2023-11-21', 200, 30.00), -- Thức ăn chó lớn (10kg)
(62, 62, 2, '2023-11-22', 250, 8.00),   -- Cát vệ sinh mèo (5kg)
(63, 63, 3, '2023-11-23', 300, 6.00),   -- Đồ chơi cắn gặm cho chó
(64, 64, 4, '2023-11-24', 350, 10.00),  -- Vòng cổ cho chó
(65, 65, 5, '2023-11-25', 500, 2.80),   -- Pate cho mèo (hộp)
(66, 66, 6, '2023-11-26', 100, 45.00);  -- Lồng vận chuyển thú cưng

-- ProductSupplier for Product IDs 67-72 (Category: Automotive Parts, Supplied by Supplier 7-12)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(67, 67, 7, '2023-11-01', 60, 100.00),  -- Lốp xe du lịch (size 17)
(68, 68, 8, '2023-11-02', 120, 40.00),  -- Dầu động cơ tổng hợp (4L)
(69, 69, 9, '2023-11-03', 80, 70.00),   -- Ắc quy ô tô
(70, 70, 10, '2023-11-04', 150, 50.00), -- Đèn pha LED ô tô
(71, 71, 11, '2023-11-05', 250, 12.00), -- Gạt mưa đa năng
(72, 72, 12, '2023-11-06', 180, 17.00); -- Bọc vô lăng da

-- ProductSupplier for Product IDs 73-78 (Category: Office Supplies, Supplied by Supplier 13-18)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(73, 73, 13, '2023-11-20', 600, 4.00),  -- Giấy A4 (ream)
(74, 74, 14, '2023-11-21', 400, 6.50),  -- Bút bi xanh (hộp 10 cây)
(75, 75, 15, '2023-11-22', 300, 5.50),  -- Sổ tay A5 bìa cứng
(76, 76, 16, '2023-11-23', 500, 3.00),  -- Kẹp giấy các loại (hộp)
(77, 77, 17, '2023-11-24', 450, 2.50),  -- Băng dính trong (cuộn)
(78, 78, 18, '2023-11-25', 120, 13.00); -- Máy tính cầm tay

-- ProductSupplier for Product IDs 79-84 (Category: Groceries - Dry Goods, Supplied by Supplier 19-20, then 1-4)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(79, 79, 19, '2023-12-01', 250, 7.00),  -- Gạo Jasmine (5kg)
(80, 80, 20, '2023-12-02', 180, 10.00), -- Mì gói Hảo Hảo (thùng)
(81, 81, 1, '2023-12-03', 200, 5.00),  -- Dầu ăn thực vật (2L)
(82, 82, 2, '2023-12-04', 350, 2.00),  -- Đường tinh luyện (1kg)
(83, 83, 3, '2023-12-05', 300, 7.50),  -- Cà phê hòa tan (hộp)
(84, 84, 4, '2023-12-06', 400, 3.20);  -- Bánh quy socola (gói)

-- ProductSupplier for Product IDs 85-90 (Category: Groceries - Fresh, Supplied by Supplier 5-10)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(85, 85, 5, '2024-01-01', 120, 2.50),  -- Táo Gala (kg)
(86, 86, 6, '2024-01-02', 60, 18.00),   -- Cá hồi tươi (kg)
(87, 87, 7, '2024-01-03', 70, 16.00),   -- Thịt bò tươi (kg)
(88, 88, 8, '2024-01-04', 250, 1.80),   -- Sữa tươi không đường (1L)
(89, 89, 9, '2024-01-05', 180, 1.20),   -- Rau xà lách (kg)
(90, 90, 10, '2024-01-06', 150, 2.30);  -- Trứng gà tươi (chục)

-- ProductSupplier for Product IDs 91-96 (Category: Tools & Home Improvement, Supplied by Supplier 11-16)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(91, 91, 11, '2023-11-15', 100, 50.00), -- Bộ dụng cụ cầm tay 50 chi tiết
(92, 92, 12, '2023-11-16', 60, 65.00),  -- Máy khoan điện cầm tay
(93, 93, 13, '2023-11-17', 120, 35.00), -- Bộ cờ lê đa năng
(94, 94, 14, '2023-11-18', 40, 80.00),  -- Thang nhôm rút gọn
(95, 95, 15, '2023-11-19', 200, 15.00), -- Hộp vít các loại
(96, 96, 16, '2023-11-20', 250, 19.00); -- Dây điện cuộn (50m)

-- ProductSupplier for Product IDs 97-102 (Category: Baby Products, Supplied by Supplier 17-20, then 1-2)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(97, 97, 17, '2023-11-25', 350, 20.00), -- Tã giấy trẻ em (size M, gói lớn)
(98, 98, 18, '2023-11-26', 180, 25.00), -- Sữa công thức cho bé (hộp 900g)
(99, 99, 19, '2023-11-27', 250, 10.00), -- Bình sữa PPSU (240ml)
(100, 100, 20, '2023-11-28', 500, 3.00), -- Khăn ướt không mùi (gói)
(101, 101, 1, '2023-11-29', 300, 18.00), -- Quần áo sơ sinh (bộ 5 món)
(102, 102, 2, '2023-11-30', 350, 6.00);  -- Đồ chơi gặm nướu silicone

-- ProductSupplier for Product IDs 103-108 (Category: Health & Wellness, Supplied by Supplier 3-8)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(103, 103, 3, '2023-11-20', 200, 19.00), -- Vitamin tổng hợp (lọ 60 viên)
(104, 104, 4, '2023-11-21', 150, 30.00), -- Dầu cá Omega-3 (lọ 90 viên)
(105, 105, 5, '2023-11-22', 80, 45.00),  -- Máy đo huyết áp điện tử
(106, 106, 6, '2023-11-23', 100, 25.00), -- Nhiệt kế hồng ngoại
(107, 107, 7, '2023-11-24', 600, 4.00),  -- Khẩu trang y tế (hộp 50 cái)
(108, 108, 8, '2023-11-25', 350, 2.50);  -- Băng gạc y tế tiệt trùng (gói)

-- ProductSupplier for Product IDs 109-114 (Category: Art & Craft Supplies, Supplied by Supplier 9-14)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(109, 109, 9, '2023-11-20', 250, 15.00), -- Bộ màu nước 24 màu
(110, 110, 10, '2023-11-21', 180, 8.00),  -- Giấy vẽ A3 (tệp 100 tờ)
(111, 111, 11, '2023-11-22', 120, 12.00), -- Bộ cọ vẽ acrylic
(112, 112, 12, '2023-11-23', 300, 6.50),  -- Đất sét tự khô (500g)
(113, 113, 13, '2023-11-24', 400, 4.00),  -- Keo dán đa năng
(114, 114, 14, '2023-11-25', 200, 6.00);  -- Kéo thủ công sắc bén

-- ProductSupplier for Product IDs 115-120 (Category: Jewelry, Supplied by Supplier 15-20)
INSERT INTO tbl_product_supplier (id, product_id, supplier_id, supply_date, supply_quantity, supply_price) VALUES
(115, 115, 15, '2023-11-05', 60, 80.00),   -- Vòng cổ bạc đính đá
(116, 116, 16, '2023-11-06', 80, 60.00),   -- Bông tai ngọc trai tự nhiên
(117, 117, 17, '2023-11-07', 25, 1000.00), -- Nhẫn vàng 18K đính kim cương
(118, 118, 18, '2023-11-08', 100, 45.00),  -- Lắc tay charm bạc
(119, 119, 19, '2023-11-09', 50, 200.00),  -- Đồng hồ đeo tay nữ sang trọng
(120, 120, 20, '2023-11-10', 15, 1200.00); -- Bộ trang sức cưới

-- ---------------------------------------------------------------------
-- PHẦN 1: Giao dịch IMPORT (120 giao dịch, ID từ 1 đến 120)
-- Mỗi giao dịch IMPORT tương ứng với một bản ghi trong tbl_product_supplier.
-- ---------------------------------------------------------------------

INSERT INTO tbl_transaction (id, quantity, status, transaction_date, type, employee_id, product_id, supplier_id) VALUES
(1, 200, 'COMPLETED', '2023-11-10 09:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 1, 1),
(2, 100, 'COMPLETED', '2023-11-12 09:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 2, 2),
(3, 60, 'COMPLETED', '2023-11-14 09:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 3, 3),
(4, 350, 'COMPLETED', '2023-11-16 09:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 4, 4),
(5, 250, 'COMPLETED', '2023-11-18 10:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 5, 5),
(6, 90, 'COMPLETED', '2023-11-19 10:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 6, 6),
(7, 50, 'COMPLETED', '2023-11-08 10:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 7, 7),
(8, 40, 'COMPLETED', '2023-11-09 10:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 8, 8),
(9, 100, 'COMPLETED', '2023-11-10 11:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 9, 9),
(10, 150, 'COMPLETED', '2023-11-11 11:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 10, 10),
(11, 70, 'COMPLETED', '2023-11-12 11:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 11, 11),
(12, 90, 'COMPLETED', '2023-11-13 11:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 12, 12),
(13, 300, 'COMPLETED', '2023-11-20 12:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 13, 13),
(14, 400, 'COMPLETED', '2023-11-21 12:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 14, 14),
(15, 500, 'COMPLETED', '2023-11-22 12:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 15, 15),
(16, 200, 'COMPLETED', '2023-11-23 12:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 16, 16),
(17, 280, 'COMPLETED', '2023-11-24 13:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 17, 17),
(18, 120, 'COMPLETED', '2023-11-25 13:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 18, 18),
(19, 30, 'COMPLETED', '2023-11-15 13:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 19, 19),
(20, 45, 'COMPLETED', '2023-11-16 13:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 20, 20),
(21, 600, 'COMPLETED', '2023-11-17 14:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 21, 1),
(22, 200, 'COMPLETED', '2023-11-18 14:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 22, 2),
(23, 350, 'COMPLETED', '2023-11-19 14:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 23, 3),
(24, 120, 'COMPLETED', '2023-11-20 14:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 24, 4),
(25, 400, 'COMPLETED', '2023-11-25 09:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 25, 5),
(26, 500, 'COMPLETED', '2023-11-26 09:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 26, 6),
(27, 300, 'COMPLETED', '2023-11-27 09:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 27, 7),
(28, 450, 'COMPLETED', '2023-11-28 09:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 28, 8),
(29, 280, 'COMPLETED', '2023-11-29 10:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 29, 9),
(30, 600, 'COMPLETED', '2023-11-30 10:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 30, 10),
(31, 120, 'COMPLETED', '2023-11-05 10:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 31, 11),
(32, 150, 'COMPLETED', '2023-11-06 10:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 32, 12),
(33, 100, 'COMPLETED', '2023-11-07 11:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 33, 13),
(34, 90, 'COMPLETED', '2023-11-08 11:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 34, 14),
(35, 200, 'COMPLETED', '2023-11-09 11:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 35, 15),
(36, 110, 'COMPLETED', '2023-11-10 11:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 36, 16),
(37, 200, 'COMPLETED', '2023-11-20 12:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 37, 17),
(38, 250, 'COMPLETED', '2023-11-21 12:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 38, 18),
(39, 120, 'COMPLETED', '2023-11-22 12:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 39, 19),
(40, 150, 'COMPLETED', '2023-11-23 12:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 40, 20),
(41, 300, 'COMPLETED', '2023-11-24 13:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 41, 1),
(42, 200, 'COMPLETED', '2023-11-25 13:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 42, 2),
(43, 20, 'COMPLETED', '2023-10-30 13:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 43, 3),
(44, 12, 'COMPLETED', '2023-10-31 13:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 44, 4),
(45, 25, 'COMPLETED', '2023-11-01 14:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 45, 5),
(46, 50, 'COMPLETED', '2023-11-02 14:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 46, 6),
(47, 30, 'COMPLETED', '2023-11-03 14:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 47, 7),
(48, 40, 'COMPLETED', '2023-11-04 14:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 48, 8),
(49, 60, 'COMPLETED', '2023-11-20 15:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 49, 9),
(50, 80, 'COMPLETED', '2023-11-21 15:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 50, 10),
(51, 40, 'COMPLETED', '2023-11-22 15:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 51, 11),
(52, 250, 'COMPLETED', '2023-11-23 15:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 52, 12),
(53, 200, 'COMPLETED', '2023-11-24 16:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 53, 13),
(54, 120, 'COMPLETED', '2023-11-25 16:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 54, 14),
(55, 150, 'COMPLETED', '2023-11-15 16:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 55, 15),
(56, 200, 'COMPLETED', '2023-11-16 16:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 56, 16),
(57, 100, 'COMPLETED', '2023-11-17 17:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 57, 17),
(58, 180, 'COMPLETED', '2023-11-18 17:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 58, 18),
(59, 250, 'COMPLETED', '2023-11-19 17:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 59, 19),
(60, 200, 'COMPLETED', '2023-11-20 17:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 60, 20),
(61, 200, 'COMPLETED', '2023-11-21 09:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 61, 1),
(62, 250, 'COMPLETED', '2023-11-22 09:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 62, 2),
(63, 300, 'COMPLETED', '2023-11-23 09:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 63, 3),
(64, 350, 'COMPLETED', '2023-11-24 09:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 64, 4),
(65, 500, 'COMPLETED', '2023-11-25 10:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 65, 5),
(66, 100, 'COMPLETED', '2023-11-26 10:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 66, 6),
(67, 60, 'COMPLETED', '2023-11-01 10:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 67, 7),
(68, 120, 'COMPLETED', '2023-11-02 10:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 68, 8),
(69, 80, 'COMPLETED', '2023-11-03 11:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 69, 9),
(70, 150, 'COMPLETED', '2023-11-04 11:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 70, 10),
(71, 250, 'COMPLETED', '2023-11-05 11:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 71, 11),
(72, 180, 'COMPLETED', '2023-11-06 11:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 72, 12),
(73, 600, 'COMPLETED', '2023-11-20 12:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 73, 13),
(74, 400, 'COMPLETED', '2023-11-21 12:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 74, 14),
(75, 300, 'COMPLETED', '2023-11-22 12:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 75, 15),
(76, 500, 'COMPLETED', '2023-11-23 12:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 76, 16),
(77, 450, 'COMPLETED', '2023-11-24 13:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 77, 17),
(78, 120, 'COMPLETED', '2023-11-25 13:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 78, 18),
(79, 250, 'COMPLETED', '2023-12-01 13:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 79, 19),
(80, 180, 'COMPLETED', '2023-12-02 13:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 80, 20),
(81, 200, 'COMPLETED', '2023-12-03 14:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 81, 1),
(82, 350, 'COMPLETED', '2023-12-04 14:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 82, 2),
(83, 300, 'COMPLETED', '2023-12-05 14:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 83, 3),
(84, 400, 'COMPLETED', '2023-12-06 14:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 84, 4),
(85, 120, 'COMPLETED', '2024-01-01 15:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 85, 5),
(86, 60, 'COMPLETED', '2024-01-02 15:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 86, 6),
(87, 70, 'COMPLETED', '2024-01-03 15:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 87, 7),
(88, 250, 'COMPLETED', '2024-01-04 15:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 88, 8),
(89, 180, 'COMPLETED', '2024-01-05 16:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 89, 9),
(90, 150, 'COMPLETED', '2024-01-06 16:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 90, 10),
(91, 100, 'COMPLETED', '2023-11-15 16:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 91, 11),
(92, 60, 'COMPLETED', '2023-11-16 16:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 92, 12),
(93, 120, 'COMPLETED', '2023-11-17 17:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 93, 13),
(94, 40, 'COMPLETED', '2023-11-18 17:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 94, 14),
(95, 200, 'COMPLETED', '2023-11-19 17:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 95, 15),
(96, 250, 'COMPLETED', '2023-11-20 17:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 96, 16),
(97, 350, 'COMPLETED', '2023-11-25 09:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 97, 17),
(98, 180, 'COMPLETED', '2023-11-26 09:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 98, 18),
(99, 250, 'COMPLETED', '2023-11-27 09:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 99, 19),
(100, 500, 'COMPLETED', '2023-11-28 09:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 100, 20),
(101, 300, 'COMPLETED', '2023-11-29 10:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 101, 1),
(102, 350, 'COMPLETED', '2023-11-30 10:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 102, 2),
(103, 200, 'COMPLETED', '2023-11-20 10:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 103, 3),
(104, 150, 'COMPLETED', '2023-11-21 10:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 104, 4),
(105, 80, 'COMPLETED', '2023-11-22 11:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 105, 5),
(106, 100, 'COMPLETED', '2023-11-23 11:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 106, 6),
(107, 600, 'COMPLETED', '2023-11-24 11:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 107, 7),
(108, 350, 'COMPLETED', '2023-11-25 11:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 108, 8),
(109, 250, 'COMPLETED', '2023-11-20 12:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 109, 9),
(110, 180, 'COMPLETED', '2023-11-21 12:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 110, 10),
(111, 120, 'COMPLETED', '2023-11-22 12:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 111, 11),
(112, 300, 'COMPLETED', '2023-11-23 12:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 112, 12),
(113, 400, 'COMPLETED', '2023-11-24 13:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 113, 13),
(114, 200, 'COMPLETED', '2023-11-25 13:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 114, 14),
(115, 60, 'COMPLETED', '2023-11-05 13:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 115, 15),
(116, 80, 'COMPLETED', '2023-11-06 13:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 116, 16),
(117, 25, 'COMPLETED', '2023-11-07 14:00:00', 'IMPORT', FLOOR(11 + RAND() * 20), 117, 17),
(118, 100, 'COMPLETED', '2023-11-08 14:15:00', 'IMPORT', FLOOR(11 + RAND() * 20), 118, 18),
(119, 50, 'COMPLETED', '2023-11-09 14:30:00', 'IMPORT', FLOOR(11 + RAND() * 20), 119, 19),
(120, 15, 'COMPLETED', '2023-11-10 14:45:00', 'IMPORT', FLOOR(11 + RAND() * 20), 120, 20);

-- ---------------------------------------------------------------------
-- PHẦN 2: Giao dịch EXPORT (100 giao dịch, ID từ 121 đến 220)
-- Các giao dịch này sẽ xuất kho các sản phẩm ngẫu nhiên.
-- ---------------------------------------------------------------------

INSERT INTO tbl_transaction (id, quantity, status, transaction_date, type, employee_id, product_id, supplier_id) VALUES
(121, 10, 'COMPLETED', '2024-01-01 09:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 1, NULL),
(122, 5, 'COMPLETED', '2024-01-02 09:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 2, NULL),
(123, 20, 'COMPLETED', '2024-01-03 10:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 3, NULL),
(124, 15, 'COMPLETED', '2024-01-04 10:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 4, NULL),
(125, 8, 'COMPLETED', '2024-01-05 11:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 5, NULL),
(126, 25, 'COMPLETED', '2024-01-06 11:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 6, NULL),
(127, 12, 'COMPLETED', '2024-01-07 12:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 7, NULL),
(128, 7, 'COMPLETED', '2024-01-08 12:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 8, NULL),
(129, 30, 'COMPLETED', '2024-01-09 13:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 9, NULL),
(130, 40, 'COMPLETED', '2024-01-10 13:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 10, NULL),
(131, 18, 'COMPLETED', '2024-01-11 14:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 11, NULL),
(132, 22, 'COMPLETED', '2024-01-12 14:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 12, NULL),
(133, 50, 'COMPLETED', '2024-01-13 15:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 13, NULL),
(134, 60, 'COMPLETED', '2024-01-14 15:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 14, NULL),
(135, 70, 'COMPLETED', '2024-01-15 16:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 15, NULL),
(136, 30, 'COMPLETED', '2024-01-16 16:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 16, NULL),
(137, 10, 'COMPLETED', '2024-01-17 09:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 17, NULL),
(138, 15, 'COMPLETED', '2024-01-18 09:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 18, NULL),
(139, 2, 'COMPLETED', '2024-01-19 10:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 19, NULL),
(140, 3, 'COMPLETED', '2024-01-20 10:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 20, NULL),
(141, 80, 'COMPLETED', '2024-01-21 11:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 21, NULL),
(142, 25, 'COMPLETED', '2024-01-22 11:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 22, NULL),
(143, 40, 'COMPLETED', '2024-01-23 12:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 23, NULL),
(144, 15, 'COMPLETED', '2024-01-24 12:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 24, NULL),
(145, 20, 'COMPLETED', '2024-01-25 13:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 25, NULL),
(146, 30, 'COMPLETED', '2024-01-26 13:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 26, NULL),
(147, 10, 'COMPLETED', '2024-01-27 14:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 27, NULL),
(148, 18, 'COMPLETED', '2024-01-28 14:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 28, NULL),
(149, 12, 'COMPLETED', '2024-01-29 15:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 29, NULL),
(150, 50, 'COMPLETED', '2024-01-30 15:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 30, NULL),
(151, 5, 'COMPLETED', '2024-02-01 09:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 31, NULL),
(152, 8, 'COMPLETED', '2024-02-02 09:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 32, NULL),
(153, 10, 'COMPLETED', '2024-02-03 10:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 33, NULL),
(154, 7, 'COMPLETED', '2024-02-04 10:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 34, NULL),
(155, 15, 'COMPLETED', '2024-02-05 11:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 35, NULL),
(156, 6, 'COMPLETED', '2024-02-06 11:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 36, NULL),
(157, 20, 'COMPLETED', '2024-02-07 12:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 37, NULL),
(158, 10, 'COMPLETED', '2024-02-08 12:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 38, NULL),
(159, 8, 'COMPLETED', '2024-02-09 13:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 39, NULL),
(160, 12, 'COMPLETED', '2024-02-10 13:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 40, NULL),
(161, 25, 'COMPLETED', '2024-02-11 14:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 41, NULL),
(162, 18, 'COMPLETED', '2024-02-12 14:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 42, NULL),
(163, 2, 'COMPLETED', '2024-02-13 15:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 43, NULL),
(164, 1, 'COMPLETED', '2024-02-14 15:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 44, NULL),
(165, 4, 'COMPLETED', '2024-02-15 09:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 45, NULL),
(166, 6, 'COMPLETED', '2024-02-16 09:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 46, NULL),
(167, 3, 'COMPLETED', '2024-02-17 10:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 47, NULL),
(168, 5, 'COMPLETED', '2024-02-18 10:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 48, NULL),
(169, 10, 'COMPLETED', '2024-02-19 11:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 49, NULL),
(170, 8, 'COMPLETED', '2024-02-20 11:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 50, NULL),
(171, 5, 'COMPLETED', '2024-02-21 12:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 51, NULL),
(172, 30, 'COMPLETED', '2024-02-22 12:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 52, NULL),
(173, 20, 'COMPLETED', '2024-02-23 13:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 53, NULL),
(174, 15, 'COMPLETED', '2024-02-24 13:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 54, NULL),
(175, 10, 'COMPLETED', '2024-02-25 14:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 55, NULL),
(176, 15, 'COMPLETED', '2024-02-26 14:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 56, NULL),
(177, 7, 'COMPLETED', '2024-02-27 15:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 57, NULL),
(178, 20, 'COMPLETED', '2024-02-28 15:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 58, NULL),
(179, 25, 'COMPLETED', '2024-02-29 09:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 59, NULL),
(180, 18, 'COMPLETED', '2024-03-01 09:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 60, NULL),
(181, 20, 'COMPLETED', '2024-03-02 10:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 61, NULL),
(182, 30, 'COMPLETED', '2024-03-03 10:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 62, NULL),
(183, 15, 'COMPLETED', '2024-03-04 11:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 63, NULL),
(184, 25, 'COMPLETED', '2024-03-05 11:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 64, NULL),
(185, 50, 'COMPLETED', '2024-03-06 12:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 65, NULL),
(186, 10, 'COMPLETED', '2024-03-07 12:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 66, NULL),
(187, 5, 'COMPLETED', '2024-03-08 13:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 67, NULL),
(188, 8, 'COMPLETED', '2024-03-09 13:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 68, NULL),
(189, 12, 'COMPLETED', '2024-03-10 14:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 69, NULL),
(190, 18, 'COMPLETED', '2024-03-11 14:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 70, NULL),
(191, 20, 'COMPLETED', '2024-03-12 15:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 71, NULL),
(192, 15, 'COMPLETED', '2024-03-13 15:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 72, NULL),
(193, 50, 'COMPLETED', '2024-03-14 09:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 73, NULL),
(194, 40, 'COMPLETED', '2024-03-15 09:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 74, NULL),
(195, 25, 'COMPLETED', '2024-03-16 10:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 75, NULL),
(196, 30, 'COMPLETED', '2024-03-17 10:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 76, NULL),
(197, 35, 'COMPLETED', '2024-03-18 11:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 77, NULL),
(198, 8, 'COMPLETED', '2024-03-19 11:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 78, NULL),
(199, 10, 'COMPLETED', '2024-03-20 12:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 79, NULL),
(200, 12, 'COMPLETED', '2024-03-21 12:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 80, NULL),
(201, 15, 'COMPLETED', '2024-03-22 13:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 81, NULL),
(202, 20, 'COMPLETED', '2024-03-23 13:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 82, NULL),
(203, 22, 'COMPLETED', '2024-03-24 14:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 83, NULL),
(204, 28, 'COMPLETED', '2024-03-25 14:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 84, NULL),
(205, 5, 'COMPLETED', '2024-03-26 15:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 85, NULL),
(206, 3, 'COMPLETED', '2024-03-27 15:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 86, NULL),
(207, 4, 'COMPLETED', '2024-03-28 09:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 87, NULL),
(208, 10, 'COMPLETED', '2024-03-29 09:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 88, NULL),
(209, 8, 'COMPLETED', '2024-03-30 10:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 89, NULL),
(210, 7, 'COMPLETED', '2024-03-31 10:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 90, NULL),
(211, 6, 'COMPLETED', '2024-04-01 11:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 91, NULL),
(212, 4, 'COMPLETED', '2024-04-02 11:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 92, NULL),
(213, 8, 'COMPLETED', '2024-04-03 12:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 93, NULL),
(214, 3, 'COMPLETED', '2024-04-04 12:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 94, NULL),
(215, 12, 'COMPLETED', '2024-04-05 13:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 95, NULL),
(216, 15, 'COMPLETED', '2024-04-06 13:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 96, NULL),
(217, 25, 'COMPLETED', '2024-04-07 14:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 97, NULL),
(218, 10, 'COMPLETED', '2024-04-08 14:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 98, NULL),
(219, 18, 'COMPLETED', '2024-04-09 15:00:00', 'EXPORT', FLOOR(11 + RAND() * 20), 99, NULL),
(220, 40, 'COMPLETED', '2024-04-10 15:30:00', 'EXPORT', FLOOR(11 + RAND() * 20), 100, NULL);