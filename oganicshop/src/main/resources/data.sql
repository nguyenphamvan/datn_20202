-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `blog`
--

DROP TABLE IF EXISTS `blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blog` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `sapo` text,
  `content` varchar(45) DEFAULT NULL,
  `img_1` varchar(45) DEFAULT NULL,
  `img_2` varchar(45) DEFAULT NULL,
  `tags` text,
  `category_blog_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_blog_category_blog1_idx` (`category_blog_id`),
  CONSTRAINT `fk_blog_category_blog1` FOREIGN KEY (`category_blog_id`) REFERENCES `category_blog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog`
--

LOCK TABLES `blog` WRITE;
/*!40000 ALTER TABLE `blog` DISABLE KEYS */;
/*!40000 ALTER TABLE `blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  `category_url` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `parent_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_url_UNIQUE` (`category_url`),
  KEY `fk_category_category1_idx` (`parent_id`),
  CONSTRAINT `fk_category_category1` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Điện thoại máy tính','dien-thoai-may-tinh',NULL,NULL,NULL),(2,'Đồng hồ - phụ kiện','dong-ho-phu-kien',NULL,NULL,NULL),(3,'Thời trang nam','thoi-trang-nam',NULL,NULL,NULL),(4,'Thời trang nữ','thoi-trang-nu',NULL,NULL,NULL),(5,'Mỹ phẩm','my-pham',NULL,NULL,NULL),(6,'Mẹ và bé - đồ chơi','me-va-be-do-choi',NULL,NULL,NULL),(7,'Tivi - máy ảnh - game','tivi-may-anh-game',NULL,NULL,NULL),(8,'Đồ gia dụng - Điện máy','dien-gia-dung-dien-may',NULL,NULL,NULL),(9,'Quà lưu niệm','qua-luu-niem',NULL,NULL,NULL),(10,'Sách','sach',NULL,NULL,NULL),(11,'Laptop','laptop',NULL,NULL,1),(12,'Điện thoại thông minh','dien-thoai-thong-minh',NULL,NULL,1),(13,'Đồng hồ nam','dong-ho-nam',NULL,NULL,2),(14,'Đồng hồ nữ','dong-ho-nu',NULL,NULL,2),(15,'Phụ kiện - trang sức nam','phu-kien-trang-suc-nam',NULL,NULL,2),(16,'Phụ kiện - trang sức nữ','phu-kien-trang-suc-nu',NULL,NULL,2),(50,'Áo vest ','ao-vest',NULL,NULL,3),(51,'Áo sơ mi','ao-so-mi',NULL,NULL,3),(52,'Quần tây','quan-tay',NULL,NULL,3),(53,'Quần jean nam','quan-jean-nam',NULL,NULL,3),(54,'Giày thể thao nam','giay-the-thao-nam',NULL,NULL,3),(55,'Giày Tây','giay-tay',NULL,NULL,3),(56,'Phụ kiện thời trang nam','phu-kien-thoi-trang-nam',NULL,NULL,3),(57,'Áo khoác nữ','ao-khoac-nu',NULL,NULL,4),(58,'Đầm - váy ','dam-vay',NULL,NULL,4),(59,'Quần jean nữ','quan-jean-nu',NULL,NULL,4),(60,'Phụ kiện thời trang nữ','phu-kien-thoi-trang-nu',NULL,NULL,4),(61,'Giày thể thao nữ','giay-the-thao-nu',NULL,NULL,4),(62,'Giày cao gót','giay-cao-got',NULL,NULL,4),(63,'Boot nữ','boot-nu',NULL,NULL,4),(64,'Đồ chơi cho bé','do-choi-cho-be',NULL,NULL,6),(65,'Thời trang cho bé','thoi-trang-cho-be',NULL,NULL,6),(66,'đồ dùng cho bé','do-dung-cho-be',NULL,NULL,6),(67,'Dinh dưỡng cho bé','dinh-duong-cho-be',NULL,NULL,6),(68,'Thời trang bà bầu','thoi-trang-ba-bau',NULL,NULL,6),(69,'Đồ dùng gia đình','do-dung-gia-dinh',NULL,NULL,8),(70,'Thiết bị điện máy','thiet bi dien may',NULL,NULL,8),(71,'Sách nuôi dạy con','sach-nuoi-day-con','2020-01-20 00:00:00',NULL,10),(72,'Sách kỹ năng sống','sach-ky-nang-song',NULL,NULL,10),(73,'Sách hoc  ngoại ngữ','sach-hoc-ngoai-ngu',NULL,NULL,10),(74,'Sách thiếu nhi','sach-thieu-nhi',NULL,NULL,10),(75,'Văn học - tiểu thuyết','van-hoc-tieu-thuyet',NULL,NULL,10),(76,'Khoa học tổng hợp','khoa-hoc-tong-hop',NULL,NULL,10),(77,'Truyện tranh','truyen-tranh',NULL,NULL,10);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_blog`
--

DROP TABLE IF EXISTS `category_blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_blog` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cate_blog_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_blog`
--

LOCK TABLES `category_blog` WRITE;
/*!40000 ALTER TABLE `category_blog` DISABLE KEYS */;
/*!40000 ALTER TABLE `category_blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `code` varchar(45) NOT NULL,
  `discount_percent` float DEFAULT NULL,
  `discount_price` int DEFAULT NULL,
  `condition` int NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `number_of_uses` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion`
--

LOCK TABLES `promotion` WRITE;
/*!40000 ALTER TABLE `promotion` DISABLE KEYS */;
/*!40000 ALTER TABLE `promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  `promotion` int DEFAULT NULL,
  `total_price` int DEFAULT NULL,
  `reviewed` tinyint DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `order_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_detail_product1_idx` (`product_id`),
  KEY `fk_order_detail_order1_idx` (`order_id`),
  CONSTRAINT `fk_order_detail_order1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_order_detail_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1,2,1800,0,1800,0,2,2),(2,4,3600,0,3600,1,2,3),(3,2,1800,0,1800,1,2,4),(4,1,800,0,800,1,5,5),(5,1,900,0,900,1,9,5),(6,1,800,0,800,1,5,6),(7,1,900,0,900,1,9,6),(8,1,900,0,900,1,6,7),(9,2,1800,0,1800,1,7,7),(10,1,800,0,800,1,5,8),(11,1,900,0,900,1,8,8),(12,1,900,0,900,1,9,9),(13,1,800,0,800,1,5,10),(14,1,900,0,900,1,9,10);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_logging`
--

DROP TABLE IF EXISTS `order_logging`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_logging` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orders_id` bigint NOT NULL,
  `status` text NOT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_log_order_status_orders1_idx` (`orders_id`),
  CONSTRAINT `fk_log_order_status_orders1` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_logging`
--

LOCK TABLES `order_logging` WRITE;
/*!40000 ALTER TABLE `order_logging` DISABLE KEYS */;
INSERT INTO `order_logging` VALUES (6,2,'Đặt hàng thành công','2021-01-24 17:00:00'),(7,2,'Đã đóng gói xong','2021-01-24 17:08:00'),(8,2,'Bàn giao vận chuyển','2021-01-24 17:08:00'),(9,2,'Đang vận chuyển','2021-01-24 17:09:00'),(10,2,'Giao hàng thành công','2021-01-24 17:12:00');
/*!40000 ALTER TABLE `order_logging` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `users_id` bigint NOT NULL,
  `contact_receiver` varchar(50) DEFAULT NULL,
  `contact_phone` varchar(45) DEFAULT NULL,
  `contact_address` text,
  `payment_method` varchar(45) DEFAULT NULL,
  `sub_total` int NOT NULL,
  `ship_fee` int NOT NULL,
  `promotion` int NOT NULL,
  `total` int NOT NULL,
  `message` text,
  `status` varchar(50) DEFAULT NULL,
  `note` text,
  `order_date` datetime DEFAULT NULL,
  `delivery_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orders_users1_idx` (`users_id`),
  CONSTRAINT `fk_orders_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (2,1,'Phạm Văn Nguyên','0947060528','Hà Nội','Thanh toán khi nhận hàng',3600,100,0,3700,'17:32 11/11/2020 - Chúng tôi vừa bàn giao đơn hàng của quý khách đến đối tác vận chuyển Tiki Team. Đơn hàng của quý khách sẽ được giao trong ngày hôm nay 11/11/2020','Giao hàng thành công','giao hàng vào cuối tuần','2021-01-25 00:00:00','2021-01-25 00:00:00'),(3,1,'Phạm Văn Nguyên','0947060528','Hà Nội','Thanh toán khi nhận hàng',7200,100,0,7300,NULL,'Giao hàng thành công','giao hàng vào cuối tuần','2021-01-25 00:00:00','2021-01-25 00:00:00'),(4,1,'Phạm Văn Nguyên','0947060528','Hà Nội','Thanh toán khi nhận hàng',3600,100,0,3700,NULL,'Giao hàng thành công','giao hàng vào cuối tuần','2021-01-25 00:00:00','2021-01-25 00:00:00'),(5,1,'Phạm Văn Nguyên','0947060528','Thanh Nhàn - Hà Nội','Thanh toán khi nhận hàng',0,0,0,1700,NULL,'Giao hàng thành công','đsgdsgdg','2021-02-08 00:00:00','2021-02-08 00:00:00'),(6,1,'Phạm Văn Nguyên','0947060528','Thanh Nhàn - Hà Nội','Thanh toán qua ví Momo',0,0,0,1700,NULL,'Giao hàng thành công','sdfsdfdsf','2021-02-08 00:00:00','2021-02-08 00:00:00'),(7,1,'Nguyên Phạm Văn','0947060528','Thanh Nhàn - Hà Nội','Thanh toán qua ví Momo',0,0,0,2700,NULL,'Giao hàng thành công','Giao hàng vào chủ nhật','2021-02-08 00:00:00','2021-02-08 00:00:00'),(8,1,'Nguyên Phạm Văn','0947060528','Ngõ Quỳnh - Thanh Nhàn - Hà Nội','Thanh toán qua ví Momo',0,0,0,2600,NULL,'Giao hàng thành công','Giao hàng vào giờ hành chính','2021-02-08 00:00:00','2021-02-08 00:00:00'),(9,1,'Phạm Văn Nguyên','0947060528','Thanh Nhàn - Hà Nội','Thanh toán khi nhận hàng',0,0,0,900,NULL,'Giao hàng thành công','dfsdfdsf','2021-02-08 00:00:00','2021-02-08 00:00:00'),(10,1,'Phạm Văn Nguyên','0947060528','Thanh Nhàn - Hà Nội','Thanh toán khi nhận hàng',0,0,0,1700,NULL,'Giao hàng thành công','không có ghi chú','2021-02-08 00:00:00','2021-02-08 00:00:00');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `supplier_id` int DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_url` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `size` text,
  `color` text,
  `base_description` text,
  `detail_description` text,
  `price` int DEFAULT NULL,
  `promotion` int DEFAULT NULL,
  `final_price` int DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `number_of_reviews` int DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_url_UNIQUE` (`product_url`),
  KEY `fk_product_category1_idx` (`category_id`),
  KEY `fk_product_supplier1_idx` (`supplier_id`),
  CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_product_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,11,3,'MÁY TÍNH ASUS ZENBOOK K25','may-tinh-asus-zenbook-k25',NULL,NULL,NULL,'THÔNG TIN SẢN PHẨM\\nGiá niêm Yết đã bao gồm VAT: 5.790.000 đ\\nHỗ trợ trả góp 0% lãi suất (liên hệ để biết thông tin chi tiết)\\nAsus E203MAH-FD004T\\nCPU: Intel N4000 (2M Cache, 2x1.1 -> 2.6GhzGHz) 14nm, 6W\\nRAM: 2GB Onboard\\nHDD: 1000GB SATA (5400RPM)\\nVGA: Intel HD Graphics 600\\nLCD: 11.6 inch - 16:9 LED backlit HD (1366 x 768) gập 180 độ\\nBrandNew 100%, Hàng chính hãng phân phối Asus VN\\n(Đã bao gồm VAT)','mô tả chi tiết sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm',1000,100,900,3,18,5,NULL,NULL),(2,11,4,'MÁY TÍNH XÁCH TAY SAMSUNG S99','may-tinh-xach-tay-samsung-s99','laptop-asus.jpg',NULL,NULL,'Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm',1000,100,900,4,15,5,NULL,NULL),(3,11,2,'MÁY ĐỂ BÀN ACER A95','may-de-ban-acer-a95',NULL,NULL,NULL,'Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm',1000,200,800,4,15,5,NULL,NULL),(4,11,5,'MÁY TÍNH XÁCH TAY HP','may-tinh-xach-tay-hp',NULL,NULL,NULL,'Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm',1000,200,800,4,15,5,NULL,NULL),(5,11,3,'ASUS E203MAH-FD004T N4000 2GB HDD1TB (FPT)','asus-e203mahfd004t-n4000-2gb-hdd1tb-fpt',NULL,NULL,NULL,'THÔNG TIN SẢN PHẨM CƠ BẢN','mô tả chi tiết sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm',1000,200,800,4,15,5,NULL,NULL),(6,11,3,'ASUS X507MA-BR064T X507MA-BR059T N5000 4GB 1TB WIN10 (FPT)','asus-x507mabr064t-x507mabr059t-n5000-4gb-1tb-win10-fpt',NULL,NULL,NULL,'HÔNG TIN SẢN PHẨM  CƠ BẢN','mô tả chi tiết sản phẩm',1000,100,900,4,15,5,NULL,NULL),(7,11,1,'DELL INSPIRON 3573 70178837 N5000 4GB 500GB 15.6\" ĐEN (PFT)','dell-inspiron-3573-70178837-n5000-4gb-500gb-15.6-den-pft',NULL,NULL,NULL,'THÔNG TIN SẢN PHẨM  CƠ BẢN','mô tả chi tiết sản phẩm',1000,100,900,4,1,5,NULL,NULL),(8,11,3,'ASUS X407UA-BV345T I3-7020U 4G 1TB 14.0HD WIN10 XÁM','asus-x407uabv345t-i37020u-4g-1tb-14.0hd-win10-xam-',NULL,NULL,NULL,'THÔNG TIN SẢN PHẨM  CƠ BẢN','mô tả chi tiết sản phẩm',1000,100,900,4,15,5,NULL,NULL),(9,11,2,'ACER SWIFT SF114-32-C9FV NX.GXQSV.002 CELERON N4000 4GB 64GB-EMMC WIN10 (FPT)','acer-swift-sf11432c9fv-nx.gxqsv.002-celeron-n4000-4gb-64gbemmc-win10-fpt',NULL,NULL,NULL,'THÔNG TIN SẢN PHẨM  CƠ BẢN','mô tả chi tiết sản phẩm',1000,100,900,4,15,5,NULL,NULL),(10,12,4,'SAMSUNG J7','samsung-j7',NULL,NULL,NULL,'Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm',1000,100,900,4,15,5,NULL,NULL),(11,12,6,'IPHONE 8 PLUS 64 GB','iphone-8-plus-64-gb',NULL,NULL,NULL,'Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm',1000,100,900,4,15,5,NULL,NULL),(12,12,6,'IPHONE 6S PLUS 99%','iphone-6s-plus-99',NULL,NULL,NULL,'Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm',1000,200,800,4,15,5,NULL,NULL),(13,12,6,'SAMSUNG GALAXY A8 STAR','samsung-galaxy-a8-star',NULL,NULL,NULL,'Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm',1000,200,800,4,15,5,NULL,NULL),(14,12,6,'SAMSUNG GALAXY A8','samsung-galaxy-a8',NULL,NULL,NULL,'Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm',1000,200,800,4,15,5,NULL,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` int NOT NULL AUTO_INCREMENT,
  `root_comment_id` int DEFAULT NULL,
  `users_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `comment` text,
  `img` varchar(45) DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `numbers_of_like` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_review_product1_idx` (`product_id`),
  KEY `fk_review_user1_idx` (`users_id`),
  KEY `fk_review_review1_idx` (`root_comment_id`),
  CONSTRAINT `fk_review_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_review_user1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,NULL,1,1,'Sản phẩm chất lượng','Chất lượng sản phẩm rất tốt trong tầm giá, ủng hộ shop',NULL,4,'2020-11-26 16:33:23','2020-11-26 16:33:23',6),(2,NULL,1,1,'đfgfdgfdsgkjdfhgkdf','Chất lượng sản phẩm rất tốt trong tầm giá, ủng hộ shop',NULL,4,'2020-11-26 16:33:23','2020-11-26 16:33:23',10),(3,NULL,1,1,'test prepend','Chất lượng sản phẩm rất tốt trong tầm giá, ủng hộ shop',NULL,5,'2020-12-03 16:58:48','2020-12-03 16:58:48',5),(4,1,2,1,'édfsdfsd','dsfsdfsdfdsf',NULL,0,'2020-12-03 17:10:15','2020-12-03 17:10:15',5),(5,1,2,1,'édfsdfsd','édfsdfsd',NULL,0,'2020-12-03 17:10:15','2020-12-03 17:10:15',5),(6,1,2,1,NULL,'bạn mua ở đâu vậy',NULL,0,'2021-03-02 17:32:30','2021-03-02 17:32:30',0),(7,2,2,1,NULL,'bạn mua ở đâu vậy',NULL,0,'2021-03-02 17:38:12','2021-03-02 17:38:12',0),(8,NULL,2,1,'sản phẩm rất ưng ý','bạn mua ở đâu vậy',NULL,4,'2021-03-02 17:41:08','2021-03-02 17:41:08',5),(9,NULL,2,1,'sản phẩm rất ưng ý','bạn mua ở đâu vậy',NULL,4,'2021-03-02 21:19:58','2021-03-02 21:19:58',4),(10,NULL,2,1,'sản phẩm rất ưng ý','bạn mua ở đâu vậy',NULL,4,'2021-03-02 21:23:34','2021-03-02 21:23:34',3),(11,2,2,1,NULL,'bạn mua ở đâu vậy',NULL,0,'2021-03-02 21:25:21','2021-03-02 21:25:21',0),(12,2,2,1,NULL,'chất lượng có ổn không bạn',NULL,0,'2021-03-02 21:31:25','2021-03-02 21:31:25',0),(13,2,2,1,NULL,'chất lượng có ổn không bạn',NULL,0,'2021-03-02 21:38:09','2021-03-02 21:38:09',0),(14,3,2,1,NULL,'chất lượng có ổn không bạn',NULL,0,'2021-03-02 21:43:59','2021-03-02 21:43:59',0),(15,NULL,1,2,'sản phẩm rất đẹp','Tiếp tục ủng hộ shop',NULL,5,'2021-03-02 21:46:48','2021-03-02 21:46:48',1),(16,9,1,1,NULL,'hahahaha',NULL,0,'2021-03-03 01:09:49','2021-03-03 01:09:49',0),(17,9,1,1,NULL,'hahahahahahahahahaah',NULL,0,'2021-03-03 21:29:18','2021-03-03 21:29:18',0),(18,9,1,1,NULL,'dgfsdgfsdklfhjwklrhewkrjkwer',NULL,0,'2021-03-03 21:37:13','2021-03-03 21:37:13',0),(19,9,1,1,NULL,'sdasdasdsad',NULL,0,'2021-03-03 21:42:53','2021-03-03 21:42:53',0),(20,9,1,1,NULL,'sàdasdasdsadsa',NULL,0,'2021-03-03 22:01:46','2021-03-03 22:01:46',0),(21,9,1,1,NULL,'sdfdsgdfhfdhfgjfhgkjjhkjghlkjkhlkjlkjl;',NULL,0,'2021-03-03 22:19:55','2021-03-03 22:19:55',0),(22,1,1,1,NULL,'sfsdgdfhfdhfghjgfjghkhjkjhl',NULL,0,'2021-03-03 22:32:19','2021-03-03 22:32:19',0),(23,3,1,1,NULL,'sdfgdsgdfgfh',NULL,0,'2021-03-03 22:38:14','2021-03-03 22:38:14',0),(24,3,1,1,NULL,'dsfsdfsdfsdfsdfsdfsdfsdf',NULL,0,'2021-03-03 22:49:27','2021-03-03 22:49:27',0),(25,3,1,1,NULL,'sdasfsadfsdfsdfdsf',NULL,0,'2021-03-03 22:53:54','2021-03-03 22:53:54',0),(26,3,1,1,NULL,'dfgreytryutyiyutkulkhjlhsfastetretreyreyredf',NULL,0,'2021-03-03 23:00:23','2021-03-03 23:00:23',0),(27,1,1,1,NULL,'sđfhfgjgfjkghkghjljhkljkl;jk',NULL,0,'2021-03-03 23:04:22','2021-03-03 23:04:22',0),(28,3,1,1,NULL,'',NULL,0,'2021-03-03 23:11:59','2021-03-03 23:11:59',0),(29,1,1,1,NULL,'ádasdasdasdasdasd',NULL,0,'2021-03-03 23:12:27','2021-03-03 23:12:27',0),(30,2,1,1,NULL,'sádgdshgdfshdfhfdhf',NULL,0,'2021-03-03 23:12:49','2021-03-03 23:12:49',0),(31,9,1,1,NULL,'sdfsdfdfdgsdgdgdg',NULL,0,'2021-03-09 23:05:59','2021-03-09 23:05:59',0),(32,2,1,1,NULL,'sdfdsfdsf',NULL,0,'2021-03-09 23:19:56','2021-03-09 23:19:56',0),(33,NULL,1,2,'Bình thường','hahahahahahaha','Capture.PNG',3,'2021-03-21 15:21:38','2021-03-21 15:21:38',0);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'Dell'),(2,'Acer'),(3,'Asus'),(4,'SamSung'),(5,'HP'),(6,'Apple');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(45) NOT NULL,
  `fullname` varchar(45) DEFAULT NULL,
  `birthday` varchar(45) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `address` text,
  `phone` varchar(45) DEFAULT NULL,
  `wishlist` text,
  `verification_code` varchar(64) DEFAULT NULL,
  `enabled` tinyint NOT NULL,
  `created_at` varchar(45) DEFAULT NULL,
  `updated_at` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'nguyen@gmail.com','$2a$10$EQ6FfUnIgSaOOslsyVyiBexV5/pkLG3S0bq0WJkCBiur/IM6uZwYm','ROLE_USER','Phạm Văn Nguyên','1998-04-11','male','số nhà 249 ngõ Quỳnh, Thanh Nhàn - Phường Quỳnh Lôi - Quận Hai Bà Trưng - Thành phố Hà Nội','0947060528','5,7,9',NULL,1,NULL,NULL),(2,'nguyenadmin@gmail.com','$2a$10$EQ6FfUnIgSaOOslsyVyiBexV5/pkLG3S0bq0WJkCBiur/IM6uZwYm','ROLE_ADMIN','Phạm Văn Nguyên',NULL,'female',NULL,NULL,'',NULL,1,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-24  7:40:08
