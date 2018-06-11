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
  `AUTHOR_NAME` varchar(200) NOT NULL,
  `BOOK_ISBN` char(13) NOT NULL,
  PRIMARY KEY (`AUTHOR_NAME`,`BOOK_ISBN`),
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
  `BOOK_ISBN` char(13) NOT NULL,
  `BOOK_TITLE` varchar(500) NOT NULL,
  `GENRE_NAME` varchar(50) NOT NULL,
  `PUBLISHER_ID` int(11) NOT NULL,
  `PUBLICATION_YEAR` year(4) NOT NULL,
  `SELLING_PRICE` double NOT NULL,
  `STOCK_QUANTITY` int(11) NOT NULL DEFAULT '0',
  `MIN_QUANTITY` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`BOOK_ISBN`),
  KEY `BOOK_TITLE` (`BOOK_TITLE`),
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `BOOKSTORE`.`BOOK_AFTER_INSERT` AFTER INSERT ON `BOOK` FOR EACH ROW
BEGIN
	IF(NEW.STOCK_QUANTITY < NEW.MIN_QUANTITY) THEN
       CALL PLACE_ORDER(NEW.BOOK_ISBN, 2 * NEW.MIN_QUANTITY - NEW.STOCK_QUANTITY, @ORDER_ID);
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
       CALL PLACE_ORDER(NEW.BOOK_ISBN, 2 * OLD.MIN_QUANTITY - NEW.STOCK_QUANTITY, @ORDER_ID);
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
  `BOOK_ISBN` char(13) NOT NULL,
  `QUANTITY` int(11) NOT NULL,
  PRIMARY KEY (`ORDER_ID`),
  KEY `BOOK_ISBN` (`BOOK_ISBN`),
  CONSTRAINT `ORDER_ibfk_1` FOREIGN KEY (`BOOK_ISBN`) REFERENCES `BOOK` (`BOOK_ISBN`)
) ENGINE=InnoDB AUTO_INCREMENT=506 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=500 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ROLE`
--

DROP TABLE IF EXISTS `ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ROLE` (
  `ROLE_TYPE` varchar(50) NOT NULL,
  PRIMARY KEY (`ROLE_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SALES`
--

DROP TABLE IF EXISTS `SALES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SALES` (
  `SALE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `USERNAME` varchar(32) NOT NULL,
  `BOOK_ISBN` char(13) NOT NULL,
  `QUANTITY` int(11) NOT NULL,
  PRIMARY KEY (`SALE_TIME`,`USERNAME`,`BOOK_ISBN`),
  KEY `SALES_ibfk_1` (`USERNAME`),
  KEY `SALES_ibfk_2` (`BOOK_ISBN`),
  CONSTRAINT `SALES_ibfk_1` FOREIGN KEY (`USERNAME`) REFERENCES `USER` (`USERNAME`),
  CONSTRAINT `SALES_ibfk_2` FOREIGN KEY (`BOOK_ISBN`) REFERENCES `BOOK` (`BOOK_ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `USERNAME` varchar(32) NOT NULL,
  `LAST_NAME` varchar(20) NOT NULL,
  `FIRST_NAME` varchar(20) NOT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `TELNO` varchar(50) NOT NULL,
  `SHIPPING_ADDRESS` varchar(500) NOT NULL,
  `ROLE_TYPE` varchar(50) NOT NULL DEFAULT 'user',
  PRIMARY KEY (`USERNAME`),
  KEY `ROLE_TYPE` (`ROLE_TYPE`),
  CONSTRAINT `USER_ibfk_1` FOREIGN KEY (`ROLE_TYPE`) REFERENCES `ROLE` (`ROLE_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'BOOKSTORE'
--

--
-- Dumping routines for database 'BOOKSTORE'
--
/*!50003 DROP PROCEDURE IF EXISTS `add_book` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_book`(IN `BOOK_ISBN`  CHAR(13) ,
IN `BOOK_TITLE`        VARCHAR(500),
IN `GENRE_NAME`        VARCHAR(50),
IN `PUB_NAME`    	   VARCHAR(200),
IN `PUBLICATION_YEAR`  YEAR,
IN `SELLING_PRICE`     DOUBLE,
IN `STOCK_QUANTITY`    INT,
IN `MIN_QUANTITY`      INT)
BEGIN
DECLARE `PUB_ID` INT;
SELECT PUBLISHER_ID INTO `PUB_ID` FROM PUBLISHER WHERE PUBLISHER_NAME = `PUB_NAME`;
INSERT INTO `BOOK` VALUES (`BOOK_ISBN`,`BOOK_TITLE`,`GENRE_NAME`,`PUB_ID`,
`PUBLICATION_YEAR`,`SELLING_PRICE`,`STOCK_QUANTITY`,`MIN_QUANTITY`);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_book_author` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_book_author`(IN ISBN CHAR(13), IN AUTHOR VARCHAR(200))
BEGIN

INSERT INTO AUTHOR VALUES (AUTHOR, ISBN);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_user` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_user`(IN `USERNAME` VARCHAR(32),
IN `PASSWORD` VARCHAR(100),
IN `LAST_NAME` VARCHAR(20),
IN `FIRST_NAME` VARCHAR(20),
IN `EMAIL` VARCHAR(100),
IN `TELNO` VARCHAR(50),
IN `SHIPPING_ADDRESS` VARCHAR(500))
BEGIN
 
START TRANSACTION;

-- Creating a new user
SET @CREATE_QUERY = CONCAT('CREATE USER "', `USERNAME`, '"@"localhost" IDENTIFIED BY "', `PASSWORD`, '"');
PREPARE STMT FROM @CREATE_QUERY;
EXECUTE STMT;

INSERT INTO `USER` VALUES (`USERNAME`, `LAST_NAME`, `FIRST_NAME`, `EMAIL`, `TELNO`, `SHIPPING_ADDRESS`, 'user');

-- Setting privileges
SET @GRANT_QUERY = CONCAT('GRANT SELECT ON BOOKSTORE.BOOK TO "', `USERNAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT SELECT ON BOOKSTORE.AUTHOR TO "', `USERNAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT SELECT ON BOOKSTORE.PUBLISHER TO "', `USERNAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.get_user_info TO "', `USERNAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.edit_user_info TO "', `USERNAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.edit_user_password TO "', `USERNAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.check_out TO "', `USERNAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

DEALLOCATE PREPARE STMT;

COMMIT;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `check_out` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `check_out`(IN `USERNAME` VARCHAR(32), IN `ISBN` CHAR(13), IN `ORDERED_QUANTITY` INT)
BEGIN

START TRANSACTION;
-- Update stock
UPDATE	`BOOK`
SET		`STOCK_QUANTITY` = `STOCK_QUANTITY` - `ORDERED_QUANTITY`
WHERE	`BOOK_ISBN` = `ISBN`;
-- Add new log
INSERT INTO `SALES` VALUES (now(), `USERNAME`, `ISBN`, `ORDERED_QUANTITY`);
COMMIT; 
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `confirm_order` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `confirm_order`(IN ORD_ID INT)
BEGIN
DECLARE ISBN CHAR(13);
DECLARE QUNT INT; 
START TRANSACTION;
SELECT BOOK_ISBN, QUANTITY INTO ISBN, QUNT FROM `ORDER` WHERE `ORDER`.ORDER_ID = ORD_ID;
UPDATE BOOK SET STOCK_QUANTITY = STOCK_QUANTITY + QUNT WHERE BOOK_ISBN = ISBN;
DELETE FROM `ORDER` WHERE `ORDER`.ORDER_ID = ORD_ID;
COMMIT;  
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_book` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_book`(IN `ISBN`  CHAR(13))
BEGIN

DELETE FROM `AUTHOR` WHERE `BOOK_ISBN` = `ISBN`;
DELETE FROM `BOOK`   WHERE `BOOK_ISBN` = `ISBN`;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_order` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_order`(IN `ORD_ID` INT)
BEGIN

DELETE FROM `ORDER` WHERE `ORDER_ID` = `ORD_ID`;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `edit_user_info` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `edit_user_info`(IN `NEW_USERNAME` VARCHAR(32),
IN `NEW_LAST` VARCHAR(20),
IN `NEW_FIRST` VARCHAR(20),
IN `NEW_EMAIL` VARCHAR(100),
IN `NEW_TELNO` VARCHAR(50),
IN `NEW_ADDRESS` VARCHAR(500))
BEGIN

UPDATE	`USER`
SET		LAST_NAME = `NEW_LAST`,
		FIRST_NAME = `NEW_FIRST`,
        EMAIL = `NEW_EMAIL`,
        TELNO = `NEW_TELNO`,
        SHIPPING_ADDRESS = `NEW_ADDRESS`
WHERE	USERNAME = `NEW_USERNAME`;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `edit_user_password` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `edit_user_password`(IN `USERNAME` VARCHAR(32), IN `PASSWORD` VARCHAR(100))
BEGIN

SET @EDIT_QUERY = CONCAT('ALTER USER "', `USERNAME`, '"@"localhost" IDENTIFIED BY "', `PASSWORD`, '" ');
PREPARE STMT FROM @EDIT_QUERY;
EXECUTE STMT;
DEALLOCATE PREPARE STMT;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_genres` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_genres`()
BEGIN

SELECT * FROM `GENRE`;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_orders` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_orders`()
BEGIN

SELECT * FROM `ORDER`;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_user_info` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_user_info`(IN `USER_NAME` VARCHAR(32))
BEGIN

SELECT * FROM `USER` WHERE `USERNAME` = `USER_NAME`;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modify_book` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `modify_book`(IN `ISBN`  CHAR(13) ,
IN `BOOK_TITLE`        VARCHAR(500),
IN `GENRE_NAME`        VARCHAR(50),
IN `PUB_NAME`    	   VARCHAR(200),
IN `PUBLICATION_YEAR`  YEAR,
IN `SELLING_PRICE`     DOUBLE,
IN `STOCK_QUANTITY`    INT,
IN `MIN_QUANTITY`      INT)
BEGIN

DECLARE `PUB_ID` INT;
SELECT PUBLISHER_ID INTO `PUB_ID` FROM PUBLISHER WHERE PUBLISHER_NAME = `PUB_NAME`;

UPDATE	`BOOK`
SET		BOOK_TITLE = `BOOK_TITLE`,
        GENRE_NAME = `GENRE_NAME`,
        PUBLISHER_ID = `PUB_ID`,
        PUBLICATION_YEAR = `PUBLICATION_YEAR`,
        SELLING_PRICE = `SELLING_PRICE`,
        STOCK_QUANTITY = `STOCK_QUANTITY`,
        MIN_QUANTITY = `MIN_QUANTITY`
WHERE	BOOK_ISBN = `ISBN`;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `place_order` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `place_order`(IN BOOK_ISBN  CHAR(13),
IN QUANTITY  INT, OUT ORDER_ID INT)
BEGIN

INSERT INTO `ORDER` (`BOOK_ISBN`,`QUANTITY`) VALUES (BOOK_ISBN, QUANTITY);
SELECT LAST_INSERT_ID() INTO ORDER_ID;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `promote_user` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `promote_user`(IN `USER_NAME` VARCHAR(32))
BEGIN

SET max_sp_recursion_depth = 255;

-- Editing user role
UPDATE	`USER`
SET		`ROLE_TYPE` = 'manager'
WHERE	`USERNAME` = `USER_NAME`;

-- Adding privileges
SET @GRANT_QUERY = CONCAT('GRANT SELECT ON BOOKSTORE.* TO "', `USER_NAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.add_book TO "', `USER_NAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.add_book_author TO "', `USER_NAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.modify_book TO "', `USER_NAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.delete_book TO "', `USER_NAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.get_orders TO "', `USER_NAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.place_order TO "', `USER_NAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.confirm_order TO "', `USER_NAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.delete_order TO "', `USER_NAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.get_genres TO "', `USER_NAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

SET @GRANT_QUERY = CONCAT('GRANT EXECUTE ON PROCEDURE BOOKSTORE.promote_user TO "', `USER_NAME`, '"@"localhost"');
PREPARE STMT FROM @GRANT_QUERY;
EXECUTE STMT;

DEALLOCATE PREPARE STMT;

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

-- Dump completed on 2018-06-11  5:46:12
