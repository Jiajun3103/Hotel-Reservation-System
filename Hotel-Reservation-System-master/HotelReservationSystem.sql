-- MySQL dump 10.13  Distrib 8.3.0, for Win64 (x86_64)
--
-- Host: localhost    Database: hotel_reservation_system
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel` (
  `hotel_id` int NOT NULL,
  `hotel_name` varchar(45) NOT NULL,
  `hotel_address` varchar(45) NOT NULL,
  PRIMARY KEY (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel`
--

LOCK TABLES `hotel` WRITE;
/*!40000 ALTER TABLE `hotel` DISABLE KEYS */;
INSERT INTO `hotel` VALUES (1,'hotel A','Melaka'),(2,'hotel B','Kuala Lumpur'),(3,'hotel C','Johor');
/*!40000 ALTER TABLE `hotel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `payment_id` int NOT NULL,
  `amount` double NOT NULL,
  `payment_method` enum('Cash','Bank') NOT NULL,
  `user_id` int NOT NULL,
  `room_id` int NOT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `payment_reservation_fk_idx` (`user_id`),
  KEY `payment_room_fk_idx` (`room_id`),
  CONSTRAINT `payment_room_fk` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`),
  CONSTRAINT `payment_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,300,'Bank',4,2),(2,500,'Bank',4,15),(3,800,'Cash',3,9);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `user_id` int NOT NULL,
  `room_id` int NOT NULL,
  `checkIn_date` date NOT NULL,
  `checkOut_date` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`,`room_id`),
  KEY `reserve_room_id_idx` (`room_id`),
  CONSTRAINT `reserve_room_id` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`),
  CONSTRAINT `reserve_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (3,9,'2024-01-14','2024-01-16'),(4,2,'2025-01-24','2025-01-26'),(4,15,'2025-03-11','2025-03-13');
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `room_id` int NOT NULL,
  `room_number` varchar(45) NOT NULL,
  `price` double NOT NULL,
  `room_type` enum('Standard','Deluxe') NOT NULL,
  `status` enum('Vacant','Reserved') NOT NULL,
  `hotel_id` int NOT NULL,
  PRIMARY KEY (`room_id`),
  KEY `room_hotel_id_idx` (`hotel_id`),
  CONSTRAINT `room_hotel_id` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'A001',300,'Standard','Vacant',1),(2,'A002',300,'Standard','Reserved',1),(3,'A003',300,'Standard','Vacant',1),(4,'A004',300,'Standard','Reserved',1),(5,'A005',300,'Standard','Vacant',1),(6,'A006',500,'Deluxe','Vacant',1),(7,'A007',500,'Deluxe','Reserved',1),(8,'A008',500,'Deluxe','Vacant',1),(9,'B001',400,'Standard','Reserved',2),(10,'B002',400,'Standard','Reserved',2),(11,'B003',400,'Standard','Vacant',2),(12,'B004',400,'Standard','Reserved',2),(13,'B005',400,'Standard','Vacant',2),(14,'B006',800,'Deluxe','Vacant',2),(15,'B007',800,'Deluxe','Reserved',2),(16,'B008',800,'Deluxe','Vacant',2),(17,'C001',350,'Standard','Vacant',3),(18,'C002',350,'Standard','Reserved',3),(19,'C003',350,'Standard','Vacant',3),(20,'C004',350,'Standard','Reserved',3),(21,'C005',350,'Standard','Vacant',3),(22,'C006',650,'Deluxe','Vacant',3),(23,'C007',650,'Deluxe','Reserved',3),(24,'C008',650,'Deluxe','Vacant',3);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` enum('customer','admin') NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'william','12345','customer'),(2,'eldhon','abc123','customer'),(3,'tan yee','abc1234','customer'),(4,'jin han','abc12345','customer'),(5,'Liza','123','customer');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-20 18:19:36
