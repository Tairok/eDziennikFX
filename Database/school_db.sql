-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: school_sb
-- ------------------------------------------------------
-- Server version	8.2.0

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
-- Table structure for table `classes_tbl`
--

DROP TABLE IF EXISTS `classes_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classes_tbl` (
  `id_classes` int NOT NULL AUTO_INCREMENT,
  `class_name` varchar(15) NOT NULL,
  PRIMARY KEY (`id_classes`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classes_tbl`
--

LOCK TABLES `classes_tbl` WRITE;
/*!40000 ALTER TABLE `classes_tbl` DISABLE KEYS */;
INSERT INTO `classes_tbl` VALUES (1,'Klasa 1F'),(2,'Klasa 3C'),(3,'Klasa 4A');
/*!40000 ALTER TABLE `classes_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grades_tbl`
--

DROP TABLE IF EXISTS `grades_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grades_tbl` (
  `id_grade` int NOT NULL AUTO_INCREMENT,
  `grade` int NOT NULL,
  `grade_Date` date NOT NULL,
  `grade_title` varchar(45) NOT NULL,
  `fk_id_user_grades_tbl` int NOT NULL,
  `fk_id_subject_grades_tbl` int NOT NULL,
  PRIMARY KEY (`id_grade`),
  KEY `fk_id_user_grades_tbl_idx` (`fk_id_user_grades_tbl`),
  KEY `fk_id_subject_grades_tbl_idx` (`fk_id_subject_grades_tbl`),
  CONSTRAINT `fk_id_subject_grades_tbl` FOREIGN KEY (`fk_id_subject_grades_tbl`) REFERENCES `subjects_tbl` (`id_subject`),
  CONSTRAINT `fk_id_user_grades_tbl` FOREIGN KEY (`fk_id_user_grades_tbl`) REFERENCES `users_tbl` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades_tbl`
--

LOCK TABLES `grades_tbl` WRITE;
/*!40000 ALTER TABLE `grades_tbl` DISABLE KEYS */;
INSERT INTO `grades_tbl` VALUES (1,3,'2023-12-23','Math Test',6,1),(2,4,'2023-12-22','Science Quiz',6,2),(3,1,'2023-12-21','English Assignment',5,3);
/*!40000 ALTER TABLE `grades_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects_tbl`
--

DROP TABLE IF EXISTS `subjects_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjects_tbl` (
  `id_subject` int NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id_subject`),
  UNIQUE KEY `subject_name_UNIQUE` (`subject_name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects_tbl`
--

LOCK TABLES `subjects_tbl` WRITE;
/*!40000 ALTER TABLE `subjects_tbl` DISABLE KEYS */;
INSERT INTO `subjects_tbl` VALUES (3,'English'),(1,'Mathematics'),(2,'Science');
/*!40000 ALTER TABLE `subjects_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_tbl`
--

DROP TABLE IF EXISTS `users_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_tbl` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` varchar(45) DEFAULT NULL,
  `fk_id_class_user_tbl` int DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  KEY `fk_id_class_user_tbl_idx` (`fk_id_class_user_tbl`),
  CONSTRAINT `fk_id_class_user_tbl` FOREIGN KEY (`fk_id_class_user_tbl`) REFERENCES `classes_tbl` (`id_classes`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_tbl`
--

LOCK TABLES `users_tbl` WRITE;
/*!40000 ALTER TABLE `users_tbl` DISABLE KEYS */;
INSERT INTO `users_tbl` VALUES (1,'Janusz','Kowalski','janush','nosacz123','nauczyciel',1),(2,'Janina','Nowak','jannowak','securepass','uczen',1),(3,'Jarek','Andrzejewski','admin','adminpass','admin',1),(5,'Wojciech','Suchodolski','major','suchodolski','uczen',2),(6,'Krzysiek','Kononowicz','konon','nibemben','uczen',2);
/*!40000 ALTER TABLE `users_tbl` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-18 12:34:59
