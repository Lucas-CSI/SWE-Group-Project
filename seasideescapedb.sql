-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: seasideescapedb
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `seasideescapedb`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `seasideescapedb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `seasideescapedb`;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `permission_level` tinyint DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `unbooked_reservation_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKntym7e6b94fguvik6t6oyqx99` (`unbooked_reservation_id`),
  CONSTRAINT `FKntym7e6b94fguvik6t6oyqx99` FOREIGN KEY (`unbooked_reservation_id`) REFERENCES `reservation` (`id`),
  CONSTRAINT `account_chk_1` CHECK ((`permission_level` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'admin@seasideescape.org','71dcf11164c760912f9457339deeb8af0da6083f77ccbbb735687288586e38c1',2,'QHxMDlsQEI6Mtlep','admin',NULL),(6,'lmbergman88@gmail.com','e8e00259424e8d2e7e481e1400e8b7549b3703fad1f27f5d6dbe6f41b13e55c0',1,'mQgXrjKC912YYEZG','tempClerk',NULL);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `discounts` decimal(38,2) DEFAULT NULL,
  `final_amount` decimal(38,2) DEFAULT NULL,
  `room_rate` decimal(38,2) DEFAULT NULL,
  `sub_total` decimal(38,2) DEFAULT NULL,
  `taxes` decimal(38,2) DEFAULT NULL,
  `total_amount` decimal(38,2) DEFAULT NULL,
  `reservation_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf7hvgw9yyvkw834i514x3lseg` (`reservation_id`),
  CONSTRAINT `FKf7hvgw9yyvkw834i514x3lseg` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill_charges`
--

DROP TABLE IF EXISTS `bill_charges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill_charges` (
  `bill_id` bigint NOT NULL,
  `charges_id` bigint NOT NULL,
  UNIQUE KEY `UK1mf48mnmkdcs0swdq2d6fl4cv` (`charges_id`),
  KEY `FKnehm5ov52oj1ra6wke0w6ii14` (`bill_id`),
  CONSTRAINT `FK429g3d36sdbb1dwe90siw09m2` FOREIGN KEY (`charges_id`) REFERENCES `charge` (`id`),
  CONSTRAINT `FKnehm5ov52oj1ra6wke0w6ii14` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_charges`
--

LOCK TABLES `bill_charges` WRITE;
/*!40000 ALTER TABLE `bill_charges` DISABLE KEYS */;
/*!40000 ALTER TABLE `bill_charges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `reservation_id` bigint NOT NULL,
  `room_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpffdq0jp6lg2cbivago8f2abm` (`reservation_id`),
  KEY `FKq83pan5xy2a6rn0qsl9bckqai` (`room_id`),
  CONSTRAINT `FKpffdq0jp6lg2cbivago8f2abm` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`id`),
  CONSTRAINT `FKq83pan5xy2a6rn0qsl9bckqai` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (1,1,15),(2,2,32),(3,2,23),(4,2,34),(5,2,6),(6,2,40),(7,3,36),(8,4,18),(9,5,68);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `charge`
--

DROP TABLE IF EXISTS `charge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `charge` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `finalized` bit(1) NOT NULL,
  `reservation_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh19c2cswhlxw253dkb2rquwjn` (`reservation_id`),
  CONSTRAINT `FKh19c2cswhlxw253dkb2rquwjn` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `charge`
--

LOCK TABLES `charge` WRITE;
/*!40000 ALTER TABLE `charge` DISABLE KEYS */;
/*!40000 ALTER TABLE `charge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_booking`
--

DROP TABLE IF EXISTS `event_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_date` datetime(6) DEFAULT NULL,
  `event_name` varchar(255) DEFAULT NULL,
  `guest_email` varchar(255) DEFAULT NULL,
  `paid` bit(1) NOT NULL,
  `venue_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb3m8glgy2alyboorw16c5phv8` (`venue_id`),
  CONSTRAINT `FKb3m8glgy2alyboorw16c5phv8` FOREIGN KEY (`venue_id`) REFERENCES `venue` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_booking`
--

LOCK TABLES `event_booking` WRITE;
/*!40000 ALTER TABLE `event_booking` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel` (
  `id` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `rating` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel`
--

LOCK TABLES `hotel` WRITE;
/*!40000 ALTER TABLE `hotel` DISABLE KEYS */;
/*!40000 ALTER TABLE `hotel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_seq`
--

DROP TABLE IF EXISTS `hotel_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_seq`
--

LOCK TABLES `hotel_seq` WRITE;
/*!40000 ALTER TABLE `hotel_seq` DISABLE KEYS */;
INSERT INTO `hotel_seq` VALUES (1);
/*!40000 ALTER TABLE `hotel_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT NULL,
  `billing_address` varchar(255) DEFAULT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  `csv` varchar(255) DEFAULT NULL,
  `expiration_date` varchar(255) DEFAULT NULL,
  `payment_date` datetime(6) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `success` bit(1) NOT NULL,
  `event_booking_id` bigint DEFAULT NULL,
  `reservation_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk5yayc9q1oypbxxkuhhp6wa7e` (`event_booking_id`),
  KEY `FKrewpj5f9v9xehy4ga8g221nw1` (`reservation_id`),
  CONSTRAINT `FKk5yayc9q1oypbxxkuhhp6wa7e` FOREIGN KEY (`event_booking_id`) REFERENCES `event_booking` (`id`),
  CONSTRAINT `FKrewpj5f9v9xehy4ga8g221nw1` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booked` bit(1) NOT NULL,
  `check_in_date` date DEFAULT NULL,
  `check_out_date` date DEFAULT NULL,
  `checked_in` bit(1) NOT NULL,
  `discount` decimal(38,2) DEFAULT NULL,
  `paid` bit(1) NOT NULL,
  `room_rate` decimal(38,2) DEFAULT NULL,
  `account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpuht7aanh4i4be0i58jofg56b` (`account_id`),
  CONSTRAINT `FKpuht7aanh4i4be0i58jofg56b` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (1,_binary '','2024-12-25','2024-12-26',_binary '\0',NULL,_binary '\0',NULL,1),(2,_binary '\0','2024-12-25','2024-12-28',_binary '\0',NULL,_binary '\0',NULL,1),(3,_binary '\0','2024-12-19','2024-12-23',_binary '\0',NULL,_binary '\0',NULL,1),(4,_binary '\0','2024-12-17','2024-12-27',_binary '\0',NULL,_binary '\0',NULL,1),(5,_binary '\0','2024-12-12','2024-12-25',_binary '\0',NULL,_binary '\0',NULL,1);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation_charges`
--

DROP TABLE IF EXISTS `reservation_charges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation_charges` (
  `reservation_id` bigint NOT NULL,
  `charges_id` bigint NOT NULL,
  UNIQUE KEY `UKh85qtmsw1ork4oewxgs39n4er` (`charges_id`),
  KEY `FK2bvb3rthfplgbf2wb3jf2a4yd` (`reservation_id`),
  CONSTRAINT `FK2bvb3rthfplgbf2wb3jf2a4yd` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`id`),
  CONSTRAINT `FKhxt5ooymcojt5khx2q5o3etbw` FOREIGN KEY (`charges_id`) REFERENCES `charge` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation_charges`
--

LOCK TABLES `reservation_charges` WRITE;
/*!40000 ALTER TABLE `reservation_charges` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservation_charges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reset_token`
--

DROP TABLE IF EXISTS `reset_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reset_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `expiry_date` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKshiutqgqq3m7hdrlmckbk4am6` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reset_token`
--

LOCK TABLES `reset_token` WRITE;
/*!40000 ALTER TABLE `reset_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `reset_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bed_type` varchar(255) DEFAULT NULL,
  `is_smoking_allowed` bit(1) NOT NULL,
  `max_rate` double NOT NULL,
  `ocean_view` bit(1) NOT NULL,
  `quality_level` tinyint DEFAULT NULL,
  `room_number` varchar(255) DEFAULT NULL,
  `theme` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `room_chk_1` CHECK ((`quality_level` between 0 and 3)),
  CONSTRAINT `room_chk_2` CHECK ((`theme` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'King',_binary '',180,_binary '\0',3,'101',1),(2,'King',_binary '\0',130,_binary '\0',2,'102',1),(3,'Queen',_binary '\0',110,_binary '\0',1,'103',2),(4,'King',_binary '\0',130,_binary '\0',2,'104',0),(5,'Queen',_binary '',110,_binary '\0',0,'105',1),(6,'Queen',_binary '\0',110,_binary '\0',1,'106',1),(7,'Queen',_binary '',120,_binary '\0',1,'107',2),(8,'Queen',_binary '\0',100,_binary '\0',0,'108',0),(9,'Queen',_binary '\0',100,_binary '\0',0,'109',1),(10,'Queen',_binary '\0',130,_binary '',1,'110',0),(11,'Queen',_binary '\0',110,_binary '\0',1,'111',0),(12,'King',_binary '\0',130,_binary '\0',2,'112',0),(13,'Queen',_binary '',110,_binary '\0',0,'113',0),(14,'Queen',_binary '\0',130,_binary '',1,'114',0),(15,'King',_binary '\0',170,_binary '',3,'115',2),(16,'King',_binary '',140,_binary '\0',2,'116',2),(17,'Queen',_binary '\0',120,_binary '',0,'117',2),(18,'Queen',_binary '\0',110,_binary '\0',1,'118',1),(19,'King',_binary '\0',130,_binary '\0',2,'119',0),(20,'King',_binary '\0',150,_binary '',2,'120',0),(21,'Queen',_binary '\0',110,_binary '\0',1,'121',2),(22,'Queen',_binary '\0',100,_binary '\0',0,'122',1),(23,'Queen',_binary '\0',130,_binary '',1,'123',2),(24,'King',_binary '\0',170,_binary '',3,'124',0),(25,'King',_binary '\0',130,_binary '\0',2,'125',0),(26,'King',_binary '',160,_binary '\0',3,'126',1),(27,'King',_binary '\0',150,_binary '\0',3,'127',0),(28,'King',_binary '\0',150,_binary '',2,'128',2),(29,'Queen',_binary '',110,_binary '\0',0,'129',1),(30,'Queen',_binary '\0',120,_binary '',0,'130',1),(31,'King',_binary '\0',170,_binary '',3,'131',0),(32,'King',_binary '\0',150,_binary '\0',3,'132',2),(33,'King',_binary '\0',150,_binary '\0',3,'133',1),(34,'Queen',_binary '\0',130,_binary '',1,'134',2),(35,'King',_binary '\0',150,_binary '\0',3,'135',0),(36,'King',_binary '\0',150,_binary '',2,'136',1),(37,'King',_binary '\0',150,_binary '',2,'137',2),(38,'Queen',_binary '\0',120,_binary '',0,'138',1),(39,'Queen',_binary '',110,_binary '\0',0,'139',2),(40,'King',_binary '\0',150,_binary '\0',3,'140',2),(41,'Queen',_binary '\0',110,_binary '\0',1,'141',2),(42,'Queen',_binary '',110,_binary '\0',0,'142',1),(43,'King',_binary '\0',150,_binary '',2,'143',1),(44,'King',_binary '',180,_binary '',3,'144',2),(45,'King',_binary '\0',150,_binary '',2,'145',2),(46,'King',_binary '\0',150,_binary '\0',3,'146',1),(47,'Queen',_binary '\0',130,_binary '',1,'147',2),(48,'King',_binary '\0',170,_binary '',3,'148',1),(49,'Queen',_binary '\0',100,_binary '\0',0,'149',1),(50,'Queen',_binary '\0',100,_binary '\0',0,'150',1),(51,'King',_binary '',160,_binary '\0',3,'151',2),(52,'Queen',_binary '\0',130,_binary '',1,'152',1),(53,'Queen',_binary '\0',120,_binary '',0,'153',1),(54,'Queen',_binary '',140,_binary '',1,'154',2),(55,'Queen',_binary '',140,_binary '',1,'155',0),(56,'Queen',_binary '\0',130,_binary '',1,'156',1),(57,'Queen',_binary '\0',130,_binary '',1,'157',0),(58,'King',_binary '\0',170,_binary '',3,'158',0),(59,'Queen',_binary '\0',120,_binary '',0,'159',1),(60,'King',_binary '\0',150,_binary '\0',3,'160',2),(61,'King',_binary '\0',150,_binary '',2,'161',2),(62,'Queen',_binary '',140,_binary '',1,'162',1),(63,'King',_binary '\0',150,_binary '\0',3,'163',0),(64,'King',_binary '\0',150,_binary '\0',3,'164',0),(65,'King',_binary '\0',150,_binary '',2,'165',1),(66,'King',_binary '\0',130,_binary '\0',2,'166',0),(67,'King',_binary '\0',130,_binary '\0',2,'167',0),(68,'Queen',_binary '\0',110,_binary '\0',1,'168',1),(69,'Queen',_binary '\0',100,_binary '\0',0,'169',2),(70,'Queen',_binary '\0',100,_binary '\0',0,'170',2),(71,'Queen',_binary '\0',100,_binary '\0',0,'171',0),(72,'Queen',_binary '',130,_binary '',0,'172',0),(73,'Queen',_binary '\0',100,_binary '\0',0,'173',1),(74,'King',_binary '',140,_binary '\0',2,'174',0),(75,'Queen',_binary '\0',110,_binary '\0',1,'175',0),(76,'King',_binary '\0',150,_binary '',2,'176',0),(77,'King',_binary '\0',130,_binary '\0',2,'177',1),(78,'King',_binary '',180,_binary '',3,'178',1),(79,'Queen',_binary '\0',110,_binary '\0',1,'179',1),(80,'King',_binary '\0',150,_binary '\0',3,'180',2),(81,'King',_binary '',160,_binary '\0',3,'181',1),(82,'Queen',_binary '\0',130,_binary '',1,'182',1),(83,'King',_binary '\0',130,_binary '\0',2,'183',2),(84,'Queen',_binary '\0',110,_binary '\0',1,'184',0),(85,'King',_binary '\0',150,_binary '\0',3,'185',0),(86,'Queen',_binary '\0',100,_binary '\0',0,'186',1),(87,'Queen',_binary '\0',100,_binary '\0',0,'187',0),(88,'King',_binary '\0',150,_binary '\0',3,'188',2),(89,'King',_binary '\0',170,_binary '',3,'189',1),(90,'Queen',_binary '\0',120,_binary '',0,'190',1),(91,'Queen',_binary '\0',120,_binary '',0,'191',0),(92,'King',_binary '\0',130,_binary '\0',2,'192',0),(93,'King',_binary '\0',130,_binary '\0',2,'193',1),(94,'Queen',_binary '\0',100,_binary '\0',0,'194',0),(95,'Queen',_binary '\0',120,_binary '',0,'195',1),(96,'King',_binary '\0',150,_binary '\0',3,'196',1),(97,'Queen',_binary '\0',100,_binary '\0',0,'197',0),(98,'Queen',_binary '\0',120,_binary '',0,'198',2),(99,'Queen',_binary '\0',130,_binary '',1,'199',1),(100,'Queen',_binary '',110,_binary '\0',1,'200',2);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venue`
--

DROP TABLE IF EXISTS `venue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venue` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `additional_charges` decimal(38,2) DEFAULT NULL,
  `base_rate` decimal(38,2) DEFAULT NULL,
  `capacity` int NOT NULL,
  `floor_number` int NOT NULL,
  `is_booked` bit(1) NOT NULL,
  `is_paid` bit(1) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venue`
--

LOCK TABLES `venue` WRITE;
/*!40000 ALTER TABLE `venue` DISABLE KEYS */;
/*!40000 ALTER TABLE `venue` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-10 23:19:39
