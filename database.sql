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
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Điện thoại máy tính','dien-thoai-may-tinh',NULL,NULL,NULL),(2,'Đồng hồ - phụ kiện','dong-ho-phu-kien',NULL,NULL,NULL),(3,'Thời trang nam','thoi-trang-nam',NULL,NULL,NULL),(4,'Thời trang nữ','thoi-trang-nu',NULL,NULL,NULL),(5,'Mỹ phẩm','my-pham',NULL,NULL,NULL),(6,'Mẹ và bé - đồ chơi','me-va-be-do-choi',NULL,NULL,NULL),(7,'Tivi - máy ảnh - game','tivi-may-anh-game',NULL,NULL,NULL),(8,'Đồ gia dụng - Điện máy','dien-gia-dung-dien-may',NULL,NULL,NULL),(9,'Quà lưu niệm','qua-luu-niem',NULL,NULL,NULL),(10,'Sách','sach',NULL,NULL,NULL),(11,'Laptop','laptop',NULL,NULL,1),(12,'Điện thoại thông minh','dien-thoai-thong-minh',NULL,NULL,1),(13,'Đồng hồ nam','dong-ho-nam',NULL,NULL,2),(14,'Đồng hồ nữ','dong-ho-nu',NULL,NULL,2),(15,'Phụ kiện - trang sức nam','phu-kien-trang-suc-nam',NULL,NULL,2),(16,'Phụ kiện - trang sức nữ','phu-kien-trang-suc-nu',NULL,NULL,2),(50,'Áo vest ','ao-vest',NULL,NULL,3),(51,'Áo sơ mi','ao-so-mi',NULL,NULL,3),(52,'Quần tây','quan-tay',NULL,NULL,3),(53,'Quần jean nam','quan-jean-nam',NULL,NULL,3),(54,'Giày thể thao nam','giay-the-thao-nam',NULL,NULL,3),(55,'Giày Tây','giay-tay',NULL,NULL,3),(56,'Phụ kiện thời trang nam','phu-kien-thoi-trang-nam',NULL,NULL,3),(57,'Áo khoác nữ','ao-khoac-nu',NULL,NULL,4),(58,'Đầm - váy ','dam-vay',NULL,NULL,4),(59,'Quần jean nữ','quan-jean-nu',NULL,NULL,4),(60,'Phụ kiện thời trang nữ','phu-kien-thoi-trang-nu',NULL,NULL,4),(61,'Giày thể thao nữ','giay-the-thao-nu',NULL,NULL,4),(62,'Giày cao gót','giay-cao-got',NULL,NULL,4),(63,'Boot nữ','boot-nu',NULL,NULL,4),(64,'Đồ chơi cho bé','do-choi-cho-be',NULL,NULL,6),(65,'Thời trang cho bé','thoi-trang-cho-be',NULL,NULL,6),(66,'đồ dùng cho bé','do-dung-cho-be',NULL,NULL,6),(67,'Dinh dưỡng cho bé','dinh-duong-cho-be',NULL,NULL,6),(68,'Thời trang bà bầu','thoi-trang-ba-bau',NULL,NULL,6),(69,'Đồ dùng gia đình','do-dung-gia-dinh',NULL,NULL,8),(70,'Thiết bị điện máy','thiet bi dien may',NULL,NULL,8),(71,'Sách nuôi dạy con','sach-nuoi-day-con','2020-01-20 00:00:00',NULL,10),(72,'Sách kỹ năng sống','sach-ky-nang-song',NULL,NULL,10),(73,'Sách hoc  ngoại ngữ','sach-hoc-ngoai-ngu',NULL,NULL,10),(74,'Sách thiếu nhi','sach-thieu-nhi',NULL,NULL,10),(75,'Văn học - tiểu thuyết','van-hoc-tieu-thuyet',NULL,NULL,10),(76,'Khoa học tổng hợp','khoa-hoc-tong-hop',NULL,NULL,10),(77,'Truyện tranh','truyen-tranh',NULL,NULL,10),(88,'phụ kiện mới','phu-kien moi','2021-04-22 00:44:45','2021-04-22 00:44:45',1);
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
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `code` varchar(45) NOT NULL,
  `discount_percent` float NOT NULL,
  `discount_price` int NOT NULL,
  `min_order_value` int NOT NULL,
  `max_discount_value` int NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `number_of_uses` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
INSERT INTO `discount` VALUES (1,'Giảm 50% cho đơn hàng đầu tiên (tối đa 50k)','FIRSTORDER50',0.5,0,0,100,NULL,NULL,0);
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
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
  `discount` int DEFAULT NULL,
  `total_price` int DEFAULT NULL,
  `reviewed` tinyint DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `order_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_detail_product1_idx` (`product_id`),
  KEY `fk_order_detail_order1_idx` (`order_id`),
  CONSTRAINT `fk_order_detail_order1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_order_detail_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1,2,1800,0,1800,1,2,2),(2,4,3600,0,3600,1,2,3),(3,2,1800,0,1800,1,2,4),(4,1,800,0,800,1,5,5),(5,1,900,0,900,1,9,5),(6,1,800,0,800,1,5,6),(7,1,900,0,900,1,9,6),(8,1,900,0,900,1,6,7),(9,2,1800,0,1800,1,7,7),(10,1,800,0,800,1,5,8),(11,1,900,0,900,1,8,8),(12,1,900,0,900,1,9,9),(13,1,800,0,800,1,5,10),(14,1,900,0,900,1,9,10),(15,1,900,0,900,0,9,11),(19,1,900,0,900,0,9,14),(20,1,900,0,900,0,9,15),(21,1,1500,0,1500,1,7,16),(22,1,900,0,900,0,9,17),(23,1,800,0,800,0,12,18),(24,1,1500,0,1500,0,7,19),(25,1,900,0,900,0,9,20);
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
  `status` int DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_log_order_status_orders1_idx` (`orders_id`),
  CONSTRAINT `fk_log_order_status_orders1` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_logging`
--

LOCK TABLES `order_logging` WRITE;
/*!40000 ALTER TABLE `order_logging` DISABLE KEYS */;
INSERT INTO `order_logging` VALUES (6,2,0,'2021-01-24 17:00:00'),(7,2,1,'2021-01-24 17:07:00'),(8,2,2,'2021-01-24 17:08:00'),(12,3,1,'2021-04-25 15:03:01'),(13,3,2,'2021-04-25 15:06:21'),(14,17,3,'2021-05-03 11:21:58');
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
  `delivery_method` varchar(45) DEFAULT NULL,
  `payment_method` varchar(45) DEFAULT NULL,
  `sub_total` int NOT NULL,
  `ship_fee` int NOT NULL,
  `discount` int NOT NULL,
  `total` int NOT NULL,
  `status` int NOT NULL,
  `message` text,
  `note` text,
  `order_date` datetime DEFAULT NULL,
  `delivery_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orders_users1_idx` (`users_id`),
  CONSTRAINT `fk_orders_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (2,1,'Phạm Văn Nguyên','0947060528','Hà Nội',NULL,'Thanh toán khi nhận hàng',3600,100,0,3700,3,'25/04/2021 21:20:42 - Số lượng hàng nhập về không đủ đáp ứng lượng đơn hàng lớn, mong quý khách thông cảm','đơn hàng đã được tiếp nhận','2021-01-25 00:00:00','2021-04-25 21:20:43'),(3,1,'Phạm Văn Nguyên','0947060528','Hà Nội',NULL,'Thanh toán khi nhận hàng',7200,100,0,7300,2,'22:06 25/04/2021 - Đơn hàng đã được giao đến bạn','giao hàng vào cuối tuần','2021-01-25 00:00:00','2021-04-25 22:06:21'),(4,1,'Phạm Văn Nguyên','0947060528','Hà Nội',NULL,'Thanh toán khi nhận hàng',3600,100,0,3700,2,'17:32 11/11/2020 - Chúng tôi vừa bàn giao đơn hàng của quý khách đến đối tác vận chuyển Tiki Team. Đơn hàng của quý khách sẽ được giao trong ngày hôm nay 11/11/2020','giao hàng vào cuối tuần','2021-01-25 00:00:00','2021-05-03 18:02:51'),(5,1,'Phạm Văn Nguyên','0947060528','Thanh Nhàn - Hà Nội',NULL,'Thanh toán khi nhận hàng',0,0,0,1700,1,'17:32 11/11/2020 - Chúng tôi vừa bàn giao đơn hàng của quý khách đến đối tác vận chuyển Tiki Team. Đơn hàng của quý khách sẽ được giao trong ngày hôm nay 11/11/2020','đsgdsgdg','2021-02-08 00:00:00','2021-02-08 00:00:00'),(6,1,'Phạm Văn Nguyên','0947060528','Thanh Nhàn - Hà Nội',NULL,'Thanh toán qua ví Momo',0,0,0,1700,1,'17:32 11/11/2020 - Chúng tôi vừa bàn giao đơn hàng của quý khách đến đối tác vận chuyển Tiki Team. Đơn hàng của quý khách sẽ được giao trong ngày hôm nay 11/11/2020','sdfsdfdsf','2021-02-08 00:00:00','2021-02-08 00:00:00'),(7,1,'Nguyên Phạm Văn','0947060528','Thanh Nhàn - Hà Nội',NULL,'Thanh toán qua ví Momo',2700,2700,0,2700,1,'17:32 11/11/2020 - Chúng tôi vừa bàn giao đơn hàng của quý khách đến đối tác vận chuyển Tiki Team. Đơn hàng của quý khách sẽ được giao trong ngày hôm nay 11/11/2020','Giao hàng vào chủ nhật','2021-02-08 00:00:00','2021-04-07 23:49:41'),(8,1,'Nguyên Phạm Văn','0947060528','Ngõ Quỳnh - Thanh Nhàn - Hà Nội',NULL,'Thanh toán qua ví Momo',0,0,0,2600,1,'17:32 11/11/2020 - Chúng tôi vừa bàn giao đơn hàng của quý khách đến đối tác vận chuyển Tiki Team. Đơn hàng của quý khách sẽ được giao trong ngày hôm nay 11/11/2020','Giao hàng vào giờ hành chính','2021-02-08 00:00:00','2021-02-08 00:00:00'),(9,1,'Phạm Văn Nguyên','0947060528','Thanh Nhàn - Hà Nội',NULL,'Thanh toán khi nhận hàng',0,0,0,900,2,'17:32 11/11/2020 - Chúng tôi vừa bàn giao đơn hàng của quý khách đến đối tác vận chuyển Tiki Team. Đơn hàng của quý khách sẽ được giao trong ngày hôm nay 11/11/2020','dfsdfdsf','2021-02-08 00:00:00','2021-02-08 00:00:00'),(10,1,'Phạm Văn Nguyên','0947060528','Thanh Nhàn - Hà Nội',NULL,'Thanh toán khi nhận hàng',0,0,0,1700,2,'17:32 11/11/2020 - Chúng tôi vừa bàn giao đơn hàng của quý khách đến đối tác vận chuyển Tiki Team. Đơn hàng của quý khách sẽ được giao trong ngày hôm nay 11/11/2020','không có ghi chú','2021-02-08 00:00:00','2021-02-08 00:00:00'),(11,2,'Phạm Văn Nguyên','0947060528','Thanh Nhàn - Hà Nội',NULL,'Thanh toán khi nhận hàng',900,0,0,900,3,'17:32 11/11/2020 - Chúng tôi vừa bàn giao đơn hàng của quý khách đến đối tác vận chuyển Tiki Team. Đơn hàng của quý khách sẽ được giao trong ngày hôm nay 11/11/2020','dsfsdfsdfsdfs','2021-03-27 22:23:26','2021-04-08 00:09:55'),(14,2,'Nguyên Phạm','undefined0947060528','Số 249 Ngõ Quỳnh - Quỳnh Lôi - Hai Bà Trưng - Hà Nộ','standard','cod',900,100,0,1000,0,'15:03 01/05/2021 - Đang vận chuyển','','2021-05-01 15:03:27','2021-05-01 15:03:27'),(15,2,'Nguyên Phạm','undefined0947060528','Số 249 Ngõ Quỳnh - Quỳnh Lôi - Hai Bà Trưng - Hà Nộ','standard','cod',900,100,0,1000,0,'15:07 01/05/2021 - Đang vận chuyển','dfsdfsdfsdfsdf','2021-05-01 15:07:00','2021-05-01 15:07:00'),(16,1,'Phạm Văn Nguyên','0947060528','số nhà 249 ngõ Quỳnh, Thanh Nhàn - Phường Quỳnh Lôi - Quận Hai Bà Trưng - Thành phố Hà Nội','standard','cod',1500,100,0,1600,3,'23:15 02/05/2021 - Đang vận chuyển','dsfsdfdf','2021-05-02 23:15:51','2021-05-03 18:04:35'),(17,1,'Phạm Văn Nguyên','0947060528','số nhà 249 ngõ Quỳnh, Thanh Nhàn - Phường Quỳnh Lôi - Quận Hai Bà Trưng - Thành phố Hà Nội','standard','cod',900,100,0,1000,3,'18:21 03/05/2021 - Bạn đã hủy đơn hàng','dsfdsfsdf','2021-05-02 23:20:50','2021-05-03 18:21:58'),(18,1,'Phạm Văn Nguyên','0947060528','số nhà 249 ngõ Quỳnh, Thanh Nhàn - Phường Quỳnh Lôi - Quận Hai Bà Trưng - Thành phố Hà Nội','standard','cod',800,100,0,900,0,'15:24 03/05/2021 - Đang vận chuyển','sdfsdfsdf','2021-05-03 15:24:39','2021-05-03 15:24:39'),(19,1,'Phạm Văn Nguyên','0947060528','số nhà 249 ngõ Quỳnh, Thanh Nhàn - Phường Quỳnh Lôi - Quận Hai Bà Trưng - Thành phố Hà Nội','standard','paypal',1500,100,0,1600,0,'15:35 03/05/2021 - Đang vận chuyển','sdfsdfsdfsdf','2021-05-03 15:35:02','2021-05-03 15:35:02'),(20,1,'Phạm Văn Nguyên','0947060528','số nhà 249 ngõ Quỳnh, Thanh Nhàn - Phường Quỳnh Lôi - Quận Hai Bà Trưng - Thành phố Hà Nội','standard','cod',900,100,0,1000,0,'15:49 03/05/2021 - Đang vận chuyển','sdsafdsfdsf','2021-05-03 15:49:27','2021-05-03 15:49:27');
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
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `base_description` text,
  `detail_description` text,
  `price` int DEFAULT NULL,
  `discount` int DEFAULT NULL,
  `final_price` int DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `number_of_reviews` int DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `stop_business` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_url_UNIQUE` (`url`),
  KEY `fk_product_category1_idx` (`category_id`),
  KEY `fk_product_supplier1_idx` (`supplier_id`),
  CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_product_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,11,4,'dsfdsfsdfsdfsdfsdfsdfsdfds','dsfdsfsdfsdfsdfsdfsdfsdfds','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','THÔNG TIN SẢN PHẨM\\nGiá niêm Yết đã bao gồm VAT: 5.790.000 đ\\nHỗ trợ trả góp 0% lãi suất (liên hệ để biết thông tin chi tiết)\\nAsus E203MAH-FD004T\\nCPU: Intel N4000 (2M Cache, 2x1.1 -> 2.6GhzGHz) 14nm, 6W\\nRAM: 2GB Onboard\\nHDD: 1000GB SATA (5400RPM)\\nVGA: Intel HD Graphics 600\\nLCD: 11.6 inch - 16:9 LED backlit HD (1366 x 768) gập 180 độ\\nBrandNew 100%, Hàng chính hãng phân phối Asus VN\\n(Đã bao gồm VAT)','<p>m&ocirc; tả chi tiết sản phẩm đ&acirc;y l&agrave; m&ocirc; tả chi tết về sản phẩm đ&acirc;y l&agrave; m&ocirc; tả chi tết về sản phẩm đ&acirc;y l&agrave; m&ocirc; tả chi tết về sản phẩm dfdfsdfsdfdfsdfdf</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n',1000,0,1000,3,15,35,0,NULL,'2021-05-05 00:00:00'),(2,11,1,'LapTop Dell','LapTop-Dell','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm ',1100,100,1000,4,15,10,0,NULL,'2021-05-04 00:00:00'),(3,11,2,'MÁY ĐỂ BÀN ACER A95','may-de-ban-acer-a95','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm',1200,200,1000,4,15,15,0,NULL,'2021-05-05 00:00:00'),(4,11,5,'MÁY TÍNH XÁCH TAY HP','may-tinh-xach-tay-hp','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm',1300,200,1100,4,15,10,0,NULL,'2021-05-05 00:00:00'),(5,11,3,'ASUS E203MAH-FD004T N4000 2GB HDD1TB (FPT)','asus-e203mahfd004t-n4000-2gb-hdd1tb-fpt','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','THÔNG TIN SẢN PHẨM CƠ BẢN','mô tả chi tiết sản phẩm đây là mô tả chi tết về sản phẩm đây là mô tả chi tết về sản phẩm',1400,200,1200,4,15,15,0,NULL,'2021-05-05 00:00:00'),(6,11,3,'ASUS X507MA-BR064T X507MA-BR059T N5000 4GB 1TB WIN10 (FPT)','asus-x507mabr064t-x507mabr059t-n5000-4gb-1tb-win10-fpt','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','HÔNG TIN SẢN PHẨM  CƠ BẢN','mô tả chi tiết sản phẩm',1500,100,1400,4,15,10,0,NULL,'2021-05-05 00:00:00'),(7,11,1,'DELL INSPIRON 3573 70178837 N5000 4GB 500GB 15.6\" ĐEN (PFT)','dell-inspiron-3573-70178837-n5000-4gb-500gb-15.6-den-pft','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','THÔNG TIN SẢN PHẨM  CƠ BẢN','mô tả chi tiết sản phẩm',1600,100,1500,4,15,20,0,NULL,'2021-05-05 00:00:00'),(8,11,3,'ASUS X407UA-BV345T I3-7020U 4G 1TB 14.0HD WIN10 XÁM','asus-x407uabv345t-i37020u-4g-1tb-14.0hd-win10-xam-','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','THÔNG TIN SẢN PHẨM  CƠ BẢN','mô tả chi tiết sản phẩm',1000,100,900,4,15,10,1,NULL,'2021-05-05 00:00:00'),(9,11,2,'ACER SWIFT SF114-32-C9FV NX.GXQSV.002 CELERON N4000 4GB 64GB-EMMC WIN10 (FPT)','acer-swift-sf11432c9fv-nx.gxqsv.002-celeron-n4000-4gb-64gbemmc-win10-fpt','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','THÔNG TIN SẢN PHẨM  CƠ BẢN','mô tả chi tiết sản phẩm',1000,100,900,4,15,5,0,NULL,'2021-05-03 00:00:00'),(10,12,4,'SAMSUNG J7','samsung-j7','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm',1000,100,900,4,15,5,0,NULL,NULL),(11,12,6,'IPHONE 8 PLUS 64 GB','iphone-8-plus-64-gb','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm',1000,100,900,4,15,5,0,NULL,NULL),(12,12,6,'IPHONE 6S PLUS 99%','iphone-6s-plus-99','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm',1000,200,800,4,15,5,1,NULL,'2021-05-03 00:00:00'),(13,12,6,'SAMSUNG GALAXY A8 STAR','samsung-galaxy-a8-star','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm',1000,200,800,4,15,5,1,NULL,NULL),(14,12,6,'SAMSUNG GALAXY A8','samsung-galaxy-a8','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','Thông số kỹ thuật cơ bản','mô tả chi tiết sản phẩm',1000,200,800,4,15,5,1,NULL,NULL),(21,12,1,'asdasdasdasdsadasd','asdasdasdasdsadasd','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','sdfsdfsdf','dfgsdfgsdfgsdfg',12000,100,11900,0,NULL,0,0,'2021-04-21 00:00:00','2021-04-21 00:00:00'),(22,12,6,'Iphone 12 Pro max','Iphone-12-Pro-max','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','sdasdasfg','sdgadsgdsghdsgsdgsdg',1200,200,1000,0,NULL,0,0,'2021-04-21 00:00:00','2021-04-21 00:00:00'),(23,12,6,'Ipad mini','Ipad-mini','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','ádadasd','agsdgsdgsdgsdgg',1200,100,1100,0,NULL,0,0,'2021-04-21 00:00:00','2021-04-21 00:00:00'),(24,12,6,'iphone sắp ra mắt','iphone-sắp-ra-mắt','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','ádasd','ádfasdfasdfasdfasdf',1200,100,1100,0,NULL,0,1,'2021-04-21 00:00:00','2021-04-22 00:00:00'),(25,88,6,'Tai nghe không dây thế hệ mới','Tai-nghe-không-dây-thế-hệ-mới','laptop-asus.jpg,laptop-asus-2.jpg,laptop-asus-3.jpg,laptop-asus-4.jpg','sadasdasdasd','ấdfaestrehrhfehhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh',1200,0,1200,0,NULL,10,0,'2021-04-26 00:00:00','2021-05-01 00:00:00');
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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,NULL,1,1,'Sản phẩm chất lượng','Chất lượng sản phẩm rất tốt trong tầm giá, ủng hộ shop',NULL,4,'2020-11-26 16:33:23','2020-11-26 16:33:23',2),(2,NULL,1,1,'đfgfdgfdsgkjdfhgkdf','Chất lượng sản phẩm rất tốt trong tầm giá, ủng hộ shop',NULL,4,'2020-11-26 16:33:23','2020-11-26 16:33:23',2),(3,NULL,1,1,'test prepend','Chất lượng sản phẩm rất tốt trong tầm giá, ủng hộ shop',NULL,5,'2020-12-03 16:58:48','2021-04-07 21:44:30',4),(4,1,2,1,'édfsdfsd','dsfsdfsdfdsf',NULL,0,'2020-12-03 17:10:15','2020-12-03 17:10:15',2),(5,1,2,1,'édfsdfsd','édfsdfsd',NULL,0,'2020-12-03 17:10:15','2020-12-03 17:10:15',2),(6,1,2,1,NULL,'bạn mua ở đâu vậy',NULL,0,'2021-03-02 17:32:30','2021-03-02 17:32:30',2),(7,2,2,1,NULL,'bạn mua ở đâu vậy',NULL,0,'2021-03-02 17:38:12','2021-03-02 17:38:12',2),(8,NULL,2,1,'sản phẩm rất ưng ý','bạn mua ở đâu vậy',NULL,4,'2021-03-02 17:41:08','2021-03-02 17:41:08',2),(9,NULL,2,1,'sản phẩm rất ưng ý','bạn mua ở đâu vậy',NULL,4,'2021-03-02 21:19:58','2021-03-02 21:19:58',2),(10,NULL,2,1,'sản phẩm rất ưng ý','bạn mua ở đâu vậy',NULL,4,'2021-03-02 21:23:34','2021-04-07 21:43:56',3),(11,2,2,1,NULL,'bạn mua ở đâu vậy',NULL,0,'2021-03-02 21:25:21','2021-03-02 21:25:21',2),(12,2,2,1,NULL,'chất lượng có ổn không bạn',NULL,0,'2021-03-02 21:31:25','2021-03-02 21:31:25',2),(13,2,2,1,NULL,'chất lượng có ổn không bạn',NULL,0,'2021-03-02 21:38:09','2021-03-02 21:38:09',2),(14,3,2,1,NULL,'chất lượng có ổn không bạn',NULL,0,'2021-03-02 21:43:59','2021-03-02 21:43:59',2),(15,NULL,1,2,'sản phẩm rất đẹp','Tiếp tục ủng hộ shop','',5,'2021-03-02 21:46:48','2021-03-02 21:46:48',2),(16,9,1,1,NULL,'hahahaha',NULL,0,'2021-03-03 01:09:49','2021-03-03 01:09:49',2),(17,9,1,1,NULL,'hahahahahahahahahaah',NULL,0,'2021-03-03 21:29:18','2021-03-03 21:29:18',2),(18,9,1,1,NULL,'dgfsdgfsdklfhjwklrhewkrjkwer',NULL,0,'2021-03-03 21:37:13','2021-03-03 21:37:13',2),(19,9,1,1,NULL,'sdasdasdsad',NULL,0,'2021-03-03 21:42:53','2021-03-03 21:42:53',22),(20,9,1,1,NULL,'sàdasdasdsadsa',NULL,0,'2021-03-03 22:01:46','2021-03-03 22:01:46',2),(21,9,1,1,NULL,'sdfdsgdfhfdhfgjfhgkjjhkjghlkjkhlkjlkjl;',NULL,0,'2021-03-03 22:19:55','2021-03-03 22:19:55',2),(22,1,1,1,NULL,'sfsdgdfhfdhfghjgfjghkhjkjhl',NULL,0,'2021-03-03 22:32:19','2021-03-03 22:32:19',2),(23,3,1,1,NULL,'sdfgdsgdfgfh',NULL,0,'2021-03-03 22:38:14','2021-03-03 22:38:14',2),(24,3,1,1,NULL,'dsfsdfsdfsdfsdfsdfsdfsdf',NULL,0,'2021-03-03 22:49:27','2021-03-03 22:49:27',2),(25,3,1,1,NULL,'sdasfsadfsdfsdfdsf',NULL,0,'2021-03-03 22:53:54','2021-03-03 22:53:54',2),(26,3,1,1,NULL,'dfgreytryutyiyutkulkhjlhsfastetretreyreyredf',NULL,0,'2021-03-03 23:00:23','2021-03-03 23:00:23',2),(27,1,1,1,NULL,'sđfhfgjgfjkghkghjljhkljkl;jk',NULL,0,'2021-03-03 23:04:22','2021-03-03 23:04:22',2),(28,3,1,1,NULL,'sđfhfgjgfjkghkghjljhkljkl;jk',NULL,0,'2021-03-03 23:11:59','2021-03-03 23:11:59',2),(29,1,1,1,NULL,'ádasdasdasdasdasd',NULL,0,'2021-03-03 23:12:27','2021-03-03 23:12:27',2),(30,2,1,1,NULL,'sádgdshgdfshdfhfdhf',NULL,0,'2021-03-03 23:12:49','2021-03-03 23:12:49',2),(31,9,1,1,NULL,'sdfsdfdfdgsdgdgdg',NULL,0,'2021-03-09 23:05:59','2021-03-09 23:05:59',2),(32,2,1,1,NULL,'sdfdsfdsf',NULL,0,'2021-03-09 23:19:56','2021-03-09 23:19:56',2),(33,15,1,2,'Bình thường','hahahahahahaha','Capture.PNG',3,'2021-03-21 15:21:38','2021-03-21 15:21:38',2),(34,15,1,2,'Bình thường','dfsdfsdfdsf','Capture.PNG',3,'2021-03-30 22:42:04','2021-03-30 22:42:04',2),(35,15,2,2,NULL,'sdasdsad','Capture.PNG',0,'2021-04-04 14:00:02','2021-04-04 14:00:02',2),(36,15,2,2,NULL,'test reply review','Capture.PNG',0,'2021-04-04 14:05:55','2021-04-04 14:05:55',2),(37,NULL,1,7,'Bình thường','ádasdasdadasdasd','Screenshot (35).png',3,'2021-05-03 22:32:35','2021-05-03 22:32:35',0);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping_address`
--

DROP TABLE IF EXISTS `shipping_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `contact_receiver` varchar(255) DEFAULT NULL,
  `contact_phone` varchar(11) DEFAULT NULL,
  `contact_address` varchar(255) DEFAULT NULL,
  `addr_default` varchar(45) DEFAULT NULL,
  `users_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_address_users1_idx` (`users_id`),
  CONSTRAINT `fk_address_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_address`
--

LOCK TABLES `shipping_address` WRITE;
/*!40000 ALTER TABLE `shipping_address` DISABLE KEYS */;
INSERT INTO `shipping_address` VALUES (1,'Phạm Văn Nguyên','0947060528','số nhà 249 ngõ Quỳnh, Thanh Nhàn - Phường Quỳnh Lôi - Quận Hai Bà Trưng - Thành phố Hà Nội','1',1),(7,'fdgsdgsdg','dgsdgsdg','sdgsgsdg - sdgsdgsdg - sgsdgsdg - sdgsdgsdg','0',1),(8,'Nguyên Phạm Test','0947060528','249 ngõ Quỳnh - Phường Quỳnh Lôi - Quận Hai Bà Trưng - Thành phố Hà Nội','0',1),(9,'Nguyên Phạm','0947060528','Số 249 Ngõ Quỳnh - Quỳnh Lôi - Hai Bà Trưng - Hà Nộ','1',2);
/*!40000 ALTER TABLE `shipping_address` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'Dell'),(2,'Acer'),(3,'Asus'),(4,'SamSung'),(5,'HP'),(6,'Apple'),(7,'Xiaomi');
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
  `phone` varchar(45) DEFAULT NULL,
  `wishlist` text,
  `verification_code` varchar(64) DEFAULT NULL,
  `enabled` tinyint NOT NULL,
  `blocked` tinyint NOT NULL,
  `created_at` varchar(45) DEFAULT NULL,
  `updated_at` varchar(45) DEFAULT NULL,
  `reset_password_token` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'nguyen@gmail.com','$2a$10$EQ6FfUnIgSaOOslsyVyiBexV5/pkLG3S0bq0WJkCBiur/IM6uZwYm','ROLE_USER','Phạm Văn Nguyên','1998-04-11','male','0947060528','1,5,8',NULL,1,0,'2021-04-11','2021-04-18',NULL),(2,'nguyenadmin@gmail.com','$2a$10$usGUwMKx1ayTx46aWxwdwe9fUdShr62ikbQSOfnlBV7Nji7cQ3yAK','ROLE_ADMIN','Phạm Văn Nguyên',NULL,'male','','9',NULL,1,0,'2021-04-11','2021-04-18',NULL),(23,'nguyenpersiet4@gmail.com','$2a$10$thVuwYm5p.7eDc/DTep3YuRAKbqN5dTJfkuRRxwcP6rICf.KKVfva','ROLE_USER','nguyen pham van',NULL,NULL,NULL,NULL,'df2ROZ3RB0Jbvo4LJX9XR08cuToPFtn6rdK23myhsg28J2BBIZqaLAiYX5KpCCj5',0,0,'2021-05-01','2021-05-01',NULL);
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

-- Dump completed on 2021-05-05  7:15:01
