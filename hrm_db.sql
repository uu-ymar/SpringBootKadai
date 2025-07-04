/*M!999999\- enable the sandbox mode */
-- MariaDB dump 10.19  Distrib 10.5.27-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: hrm_db
-- ------------------------------------------------------
-- Server version       10.5.27-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `M_DIVISION`
--

DROP TABLE IF EXISTS `M_DIVISION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `M_DIVISION` (
  `d_id` char(6) NOT NULL,
  `delete_flag` int(11) NOT NULL DEFAULT 0,
  `d_name` varchar(20) NOT NULL,
  PRIMARY KEY (`d_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `M_DIVISION`
--

LOCK TABLES `M_DIVISION` WRITE;
/*!40000 ALTER TABLE `M_DIVISION` DISABLE KEYS */;
INSERT INTO `M_DIVISION` VALUES ('DVI001',0,'開発部'),('DVI002',0,'管理部'),('DVI003',0,'営業部'),('DVI004',0,'総務部');
/*!40000 ALTER TABLE `M_DIVISION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `M_USER`
--

DROP TABLE IF EXISTS `M_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `M_USER` (
  `admin_id` varchar(254) NOT NULL,
  `admin_name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `create_day` date NOT NULL DEFAULT curdate(),
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `M_USER`
--

LOCK TABLES `M_USER` WRITE;
/*!40000 ALTER TABLE `M_USER` DISABLE KEYS */;
INSERT INTO `M_USER` VALUES ('test@mail.com','ゴンザレス','0d6be69b264717f2dd33652e212b173104b4a647b7c11ae72e9885f11cd312fb','2025-06-19');
/*!40000 ALTER TABLE `M_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_CUSTOMER`
--

DROP TABLE IF EXISTS `T_CUSTOMER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_CUSTOMER` (
  `c_id` varchar(10) NOT NULL,
  `company` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `c_tel` varchar(16) NOT NULL,
  `c_email` varchar(254) NOT NULL,
  `person` varchar(50) NOT NULL,
  `c_created_day` date NOT NULL DEFAULT curdate(),
  `c_update_day` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `c_created_name` varchar(254) NOT NULL,
  `c_update_name` varchar(254) DEFAULT NULL,
  `delete_flag` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_CUSTOMER`
--

LOCK TABLES `T_CUSTOMER` WRITE;
/*!40000 ALTER TABLE `T_CUSTOMER` DISABLE KEYS */;
INSERT INTO `T_CUSTOMER` VALUES ('CUS0001','株式会社未来開発','東京都渋谷区1-2-8','03-1234-5678','mirai@example.com','佐藤健次郎','2025-06-20','2025-06-25 09:45:00','test@mail.com','test@mail.com',0),('CUS0002','合同会社グローバルリンク','
大阪府大阪市北区1-2-4','06-2345-6789','glink@example.com','鈴木麻衣','2025-06-20','2025-06-25 09:43:48','test@mail.com',NULL,0),('CUS0003','テックプランナー株式会社','神奈川県横浜市西区2-3-4','045-345-6789','techplan@example.com','山田翔太','2025-06-20','2025-06-25 09:43:48','test@mail.com',NULL,0),('CUS0004','クリエイトデザイン合同会社','福岡県福岡市中央区4-5-6','092-456-7890','cdesign@example.com','高橋友香','2025-06-20','2025-06-25 09:43:48','test@mail.com',NULL,0),('CUS0005','日本物流システム株式会社','愛知県名古屋市中区5-6-7','052-567-8901','nbutsuryu@example.com','田中義明','2025-06-20','2025-06-25 09:43:48','test@mail.com',NULL,0),('CUS0006','みゃくみゃく株式会社','大阪府大阪市此花区夢洲中','080-9999-1111','myakumaku@mail.com','岸和田太郎','2025-06-20','2025-06-25 09:43:48','test@mail.com',NULL,0),('CUS0007','ABC有限会社','新潟県新潟市北区','010-9999-5555','abc@gmail.com','雪つり','2025-06-23','2025-06-25 09:43:48','test@mail.com',NULL,0),('CUS0008','祇園株式会社','京都府京都市右京区','080-7878-9898','kyouto.maturi@mail.com','京都太郎','2025-06-24','2025-06-25 09:45:07','test@mail.com','test@mail.com',0),('CUS0009','兼六園株式会社','石川県金沢市','090-9999-1111','taro.inoue@mail.com','金沢太郎','2025-06-24','2025-06-25 09:45:13','test@mail.com','test@mail.com',0),('CUS0010','広島株式会社','広島県広島市','080-7878-9898','kyouto.maturi@mail.com','広島太郎','2025-06-25','2025-06-25 10:00:03','test@mail.com','test@mail.com',0),('CUS0011','福岡株式会社','福岡県福岡市中央区赤坂','090-1111-1111','jirou.ito@gmail.com','福岡太郎','2025-07-02','2025-07-02 07:51:24','ゴンザレス',NULL,0),('CUS0012','鹿児島中央産業','鹿児島県奄美市','090-1111-1111','jirou.ito@gmail.com','奄美太郎','2025-07-02','2025-07-02 08:01:09','test@mail.com',NULL,0);
/*!40000 ALTER TABLE `T_CUSTOMER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_EMPLOYEE`
--

DROP TABLE IF EXISTS `T_EMPLOYEE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_EMPLOYEE` (
  `e_id` varchar(10) NOT NULL,
  `e_name` varchar(50) NOT NULL,
  `e_kana` varchar(50) NOT NULL,
  `birthday` date NOT NULL,
  `d_id` char(6) DEFAULT NULL,
  `e_tel` varchar(16) NOT NULL,
  `e_email` varchar(254) NOT NULL,
  `e_created_day` date NOT NULL DEFAULT curdate(),
  `e_update_day` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `e_created_name` varchar(254) NOT NULL,
  `e_update_name` varchar(254) DEFAULT NULL,
  `delete_flag` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`e_id`),
  KEY `FK_division_id` (`d_id`),
  CONSTRAINT `FK_division_id` FOREIGN KEY (`d_id`) REFERENCES `M_DIVISION` (`d_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_EMPLOYEE`
--

LOCK TABLES `T_EMPLOYEE` WRITE;
/*!40000 ALTER TABLE `T_EMPLOYEE` DISABLE KEYS */;
INSERT INTO `T_EMPLOYEE` VALUES ('EMP0001','田中太郎','たなかたろう','2025-06-01','DVI001','090-9999-1111','tanaka@example.com','2025-06-20','2025-06-25 09:42:32','test@mail.com','test@mail.com',0),('EMP0002','佐藤花子','さとうはなこ','1988-05-22','DVI002','080-2345-6789','sato@example.com','2025-06-20','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0003','鈴木一郎','すずきいちろう','1992-07-03','DVI003','070-3456-7890','suzuki@example.com','2025-06-20','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0004','高橋美咲','たかはしみさき','1995-12-11','DVI004','090-4567-8901','takahashi@example.com','2025-06-20','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0005','山田太一','やまだたいち','1991-03-08','DVI001','080-5678-9012','yamada@example.com','2025-06-20','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0006','伊藤五郎','いとうごろう','2000-12-24','DVI002','010-9999-5555','itogoro@gmail.com','2025-06-20','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0007','上田次郎','うえだじろう','1968-09-09','DVI003','090-1233-4566','jiroueda@gmail.com','2025-06-20','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0008','山田直子','やまだなおこ','2025-06-01','DVI001','010-9999-5555','yamada@gmail.com','2025-06-23','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0009','田中田中','たなかたなか','2025-06-07','DVI001','010-9999-5555','tanaka@gmail.com','2025-06-23','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0010','井上太郎','いのうえたろう','1964-10-01','DVI001','090-9999-1111','taro
/*M!999999\- enable the sandbox mode */
-- MariaDB dump 10.19  Distrib 10.5.27-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: hrm_db
-- ------------------------------------------------------
-- Server version       10.5.27-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0
*/;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `M_DIVISION`
--

DROP TABLE IF EXISTS `M_DIVISION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `M_DIVISION` (
  `d_id` char(6) NOT NULL,
  `delete_flag` int(11) NOT NULL DEFAULT 0,
  `d_name` varchar(20) NOT NULL,
  PRIMARY KEY (`d_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `M_DIVISION`
--

LOCK TABLES `M_DIVISION` WRITE;
/*!40000 ALTER TABLE `M_DIVISION` DISABLE KEYS */;
INSERT INTO `M_DIVISION` VALUES ('DVI001',0,'開発部'),('DVI002',0,'管理部'),('DVI003',0,'営業部'),('DVI004',0,'総務部');
/*!40000 ALTER TABLE `M_DIVISION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `M_USER`
--

DROP TABLE IF EXISTS `M_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `M_USER` (
  `admin_id` varchar(254) NOT NULL,
  `admin_name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `create_day` date NOT NULL DEFAULT curdate(),
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `M_USER`
--

LOCK TABLES `M_USER` WRITE;
/*!40000 ALTER TABLE `M_USER` DISABLE KEYS */;
INSERT INTO `M_USER` VALUES ('test@mail.com','ゴンザレス','0d6be69b264717f2dd33652e212b173104b4a647b7c11ae72e9885f11cd312fb','2025-06-19');
/*!40000 ALTER TABLE `M_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_CUSTOMER`
--

DROP TABLE IF EXISTS `T_CUSTOMER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_CUSTOMER` (
  `c_id` varchar(10) NOT NULL,
  `company` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `c_tel` varchar(16) NOT NULL,
  `c_email` varchar(254) NOT NULL,
  `person` varchar(50) NOT NULL,
  `c_created_day` date NOT NULL DEFAULT curdate(),
  `c_update_day` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `c_created_name` varchar(254) NOT NULL,
  `c_update_name` varchar(254) DEFAULT NULL,
  `delete_flag` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_CUSTOMER`
--

LOCK TABLES `T_CUSTOMER` WRITE;
/*!40000 ALTER TABLE `T_CUSTOMER` DISABLE KEYS */;
INSERT INTO `T_CUSTOMER` VALUES ('CUS0001','株式会社未来開発','東京都渋谷区1-2-8','03-1234-5678','mirai@example.com','佐藤健次郎','2025-06-20','2025-06-25 09:45:00','test@mail.com','test@mail.com',0),('CUS0002','合同会社グローバルリンク','
大阪府大阪市北区1-2-4','06-2345-6789','glink@example.com','鈴木麻衣','2025-06-20','2025-06-25 09:43:48','test@mail.com',NULL,0),('CUS0003','テックプランナー株式会社','神奈川県横浜市西区2-3-4','045-345-6789','techplan@example.com','山田翔太','2025-06-20','2025-06-25 09:43:48','test@mail.com',NULL,0),('CUS0004','クリエイトデザイン合同会社','福岡県福岡市中央区4-5-6','092-456-7890','cdesign@example.com','高橋友香','2025-06-20','2025-06-25 09:43:48','test@mail.com',NULL,0),('CUS0005','日本物流システム株式会社','愛知県名古屋市中区5-6-7','052-567-8901','nbutsuryu@example.com','田中義明','2025-06-20','2025-06-25 09:43:48','test@mail.com',NULL,0),('CUS0006','みゃくみゃく株式会社','大阪府大阪市此花区夢洲中','080-9999-1111','myakumaku@mail.com','岸和田太郎','2025-06-20','2025-06-25 09:43:48','test@mail.com',NULL,0),('CUS0007','ABC有限会社','新潟県新潟市北区','010-9999-5555','abc@gmail.com','雪つり','2025-06-23','2025-06-25 09:43:48','test@mail.com',NULL,0),('CUS0008','祇園株式会社','京都府京都市右京区','080-7878-9898','kyouto.maturi@mail.com','京都太郎','2025-06-24','2025-06-25 09:45:07','test@mail.com','test@mail.com',0),('CUS0009','兼六園株式会社','石川県金沢市','090-9999-1111','taro.inoue@mail.com','金沢太郎','2025-06-24','2025-06-25 09:45:13','test@mail.com','test@mail.com',0),('CUS0010','広島株式会社','広島県広島市','080-7878-9898','kyouto.maturi@mail.com','広島太郎','2025-06-25','2025-06-25 10:00:03','test@mail.com','test@mail.com',0),('CUS0011','福岡株式会社','福岡県福岡市中央区赤坂','090-1111-1111','jirou.ito@gmail.com','福岡太郎','2025-07-02','2025-07-02 07:51:24','ゴンザレス',NULL,0),('CUS0012','鹿児島中央産業','鹿児島県奄美市','090-1111-1111','jirou.ito@gmail.com','奄美太郎','2025-07-02','2025-07-02 08:01:09','test@mail.com',NULL,0);
/*!40000 ALTER TABLE `T_CUSTOMER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_EMPLOYEE`
--

DROP TABLE IF EXISTS `T_EMPLOYEE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_EMPLOYEE` (
  `e_id` varchar(10) NOT NULL,
  `e_name` varchar(50) NOT NULL,
  `e_kana` varchar(50) NOT NULL,
  `birthday` date NOT NULL,
  `d_id` char(6) DEFAULT NULL,
  `e_tel` varchar(16) NOT NULL,
  `e_email` varchar(254) NOT NULL,
  `e_created_day` date NOT NULL DEFAULT curdate(),
  `e_update_day` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `e_created_name` varchar(254) NOT NULL,
  `e_update_name` varchar(254) DEFAULT NULL,
  `delete_flag` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`e_id`),
  KEY `FK_division_id` (`d_id`),
  CONSTRAINT `FK_division_id` FOREIGN KEY (`d_id`) REFERENCES `M_DIVISION` (`d_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_EMPLOYEE`
--

LOCK TABLES `T_EMPLOYEE` WRITE;
/*!40000 ALTER TABLE `T_EMPLOYEE` DISABLE KEYS */;
INSERT INTO `T_EMPLOYEE` VALUES ('EMP0001','田中太郎','たなかたろう','2025-06-01','DVI001','090-9999-1111','tanaka@example.com','2025-06-20','2025-06-25 09:42:32','test@mail.com','test@mail.com',0),('EMP0002','佐藤花子','さとうはなこ','1988-05-22','DVI002','080-2345-6789','sato@example.com','2025-06-20','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0003','鈴木一郎','すずきいちろう','1992-07-03','DVI003','070-3456-7890','suzuki@example.com','2025-06-20','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0004','高橋美咲','たかはしみさき','1995-12-11','DVI004','090-4567-8901','takahashi@example.com','2025-06-20','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0005','山田太一','やまだたいち','1991-03-08','DVI001','080-5678-9012','yamada@example.com','2025-06-20','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0006','伊藤五郎','いとうごろう','2000-12-24','DVI002','010-9999-5555','itogoro@gmail.com','2025-06-20','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0007','上田次郎','うえだじろう','1968-09-09','DVI003','090-1233-4566','jiroueda@gmail.com','2025-06-20','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0008','山田直子','やまだなおこ','2025-06-01','DVI001','010-9999-5555','yamada@gmail.com','2025-06-23','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0009','田中田中','たなかたなか','2025-06-07','DVI001','010-9999-5555','tanaka@gmail.com','2025-06-23','2025-06-25 09:41:40','test@mail.com',NULL,0),('EMP0010','井上太郎','いのうえたろう','1964-10-01','DVI001','090-9999-1111','taro.inoue@mail.com','2025-06-24','2025-06-25 09:42:53','test@mail.com','test@mail.com',0),('EMP0011','田中山田','たなかやまだ','1990-01-01','DVI002','090-9999-1111','tanakayamada@mail.com','2025-06-24','2025-06-25 09:42:59','test@mail.com','test@mail.com',0),('EMP0012','渋谷太郎','しぶやたろう','2000-09-05','DVI004','080-7878-9898','shibuya.taro@mail.com','2025-06-25','2025-06-25 10:00:23','test@mail.com','test@mail.com',0),('EMP0013','田中太郎','たなかたろう','2000-07-07','DVI001','080-1111-9999','taro.tanaka@gmail.com','2025-07-02','2025-07-02 13:51:25','ゴンザレス',NULL,0),('EMP0014','山田山田','やまだやまだ','2005-04-09','DVI001','090-5555-1598','jirou.ito@gmail.com','2025-07-02','2025-07-02 08:04:13','test@mail.com',NULL,0);
/*!40000 ALTER TABLE `T_EMPLOYEE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-04 15:10:20