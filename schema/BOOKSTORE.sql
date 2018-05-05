CREATE DATABASE  IF NOT EXISTS `BOOKSTORE` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `BOOKSTORE`;
-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: localhost    Database: BOOKSTORE
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `AUTHOR`
--

DROP TABLE IF EXISTS `AUTHOR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AUTHOR` (
  `AUTHOR_ID` int(11) NOT NULL AUTO_INCREMENT,
  `AUTHOR_NAME` varchar(200) NOT NULL,
  `BOOK_ISBN` int(11) NOT NULL,
  PRIMARY KEY (`AUTHOR_ID`,`BOOK_ISBN`),
  KEY `BOOK_ISBN` (`BOOK_ISBN`),
  CONSTRAINT `AUTHOR_ibfk_1` FOREIGN KEY (`BOOK_ISBN`) REFERENCES `BOOK` (`BOOK_ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BOOK`
--

DROP TABLE IF EXISTS `BOOK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BOOK` (
  `BOOK_ISBN` int(11) NOT NULL,
  `BOOK_TITLE` varchar(500) NOT NULL,
  `PUBLISHER_ID` int(11) NOT NULL,
  `GENRE_NAME` varchar(50) NOT NULL,
  `PUBLICATION_YEAR` year(4) NOT NULL,
  `SELLING_PRICE` double NOT NULL,
  `STOCK_QUANTITY` int(11) NOT NULL DEFAULT '0',
  `MIN_QUANTITY` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`BOOK_ISBN`),
  UNIQUE KEY `BOOK_TITLE` (`BOOK_TITLE`),
  KEY `PUBLISHER_ID` (`PUBLISHER_ID`),
  KEY `GENRE_NAME` (`GENRE_NAME`),
  KEY `SELLING_PRICE` (`SELLING_PRICE`),
  CONSTRAINT `BOOK_ibfk_1` FOREIGN KEY (`PUBLISHER_ID`) REFERENCES `PUBLISHER` (`PUBLISHER_ID`),
  CONSTRAINT `BOOK_ibfk_2` FOREIGN KEY (`GENRE_NAME`) REFERENCES `GENRE` (`GENRE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `BOOKSTORE`.`BOOK_BEFORE_UPDATE` BEFORE UPDATE ON `BOOK` FOR EACH ROW
BEGIN
  DECLARE out_of_stock CONDITION FOR SQLSTATE '45000';
  IF (NEW.STOCK_QUANTITY < 0)THEN
  SIGNAL out_of_stock SET MESSAGE_TEXT ='Current stock is not enough!';
  END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `BOOKSTORE`.`BOOK_AFTER_UPDATE` AFTER UPDATE ON `BOOK` FOR EACH ROW
BEGIN
    IF(NEW.STOCK_QUANTITY < OLD.MIN_QUANTITY)THEN
       CALL PLACE_ORDER(NEW.BOOK_ISBN,OLD.MIN_QUANTITY - NEW.STOCK_QUANTITY);
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `GENRE`
--

DROP TABLE IF EXISTS `GENRE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GENRE` (
  `GENRE_NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`GENRE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ORDER`
--

DROP TABLE IF EXISTS `ORDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORDER` (
  `ORDER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BOOK_ISBN` int(11) NOT NULL,
  `QUANTITY` int(11) NOT NULL,
  PRIMARY KEY (`ORDER_ID`),
  KEY `BOOK_ISBN` (`BOOK_ISBN`),
  CONSTRAINT `ORDER_ibfk_1` FOREIGN KEY (`BOOK_ISBN`) REFERENCES `BOOK` (`BOOK_ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PUBLISHER`
--

DROP TABLE IF EXISTS `PUBLISHER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PUBLISHER` (
  `PUBLISHER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PUBLISHER_NAME` varchar(200) NOT NULL,
  `PUBLISHER_ADDRESS` varchar(500) NOT NULL,
  `PUBLISHER_TELNO` varchar(50) NOT NULL,
  PRIMARY KEY (`PUBLISHER_ID`),
  KEY `PUBLISHER_NAME` (`PUBLISHER_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'BOOKSTORE'
--

--
-- Dumping routines for database 'BOOKSTORE'
--
/*!50003 DROP PROCEDURE IF EXISTS `ADD_BOOK` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ADD_BOOK`(IN `BOOK_ISBN`  INT ,
IN `BOOK_TITLE`        VARCHAR(500),
IN `PUBLISHER_ID`      INT ,
IN `GENRE_NAME`        VARCHAR(50),
IN `PUBLICATION_YEAR`  YEAR,
IN `SELLING_PRICE`     DOUBLE,
IN `STOCK_QUANTITY`    INT,
IN `MIN_QUANTITY`      INT)
BEGIN
INSERT INTO `BOOK` VALUES (`BOOK_ISBN`,`BOOK_TITLE`,`PUBLISHER_ID`,`GENRE_NAME`,
`PUBLICATION_YEAR`,`SELLING_PRICE`,`STOCK_QUANTITY`,`MIN_QUANTITY`);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CONFIRM_ORDER` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CONFIRM_ORDER`(IN ORDER_ID INT)
BEGIN
DECLARE ISBN INT;
DECLARE QUANTITY INT;
DECLARE CONTINUE HANDLER FOR SQLEXCEPTION rollback; 
START TRANSACTION;
SELECT BOOK_ISBN , QUANTITY INTO ISBN , QUANTITY FROM `ORDER` WHERE `ORDER`.ORDER_ID = ORDER_ID;
UPDATE BOOK SET STOCK_QUANTITY = STOCK_QUANTITY + QUANTITY WHERE BOOK_ISBN = ISBN;
DELETE FROM `ORDER` WHERE `ORDER`.ORDER_ID = ORDER_ID;
COMMIT;  
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PLACE_ORDER` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `PLACE_ORDER`(IN BOOK_ISBN  INT,
IN QUANTITY  INT)
BEGIN
INSERT INTO `ORDER` (`BOOK_ISBN`,`QUANTITY`) VALUES (BOOK_ISBN,QUANTITY);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-05 12:20:01
