-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: board
-- ------------------------------------------------------
-- Server version	5.7.31-log

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
-- Table structure for table `announcements`
--

DROP TABLE IF EXISTS `announcements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author_id` int(11) DEFAULT NULL,
  `city` varchar(32) NOT NULL,
  `category` varchar(64) NOT NULL,
  `name` varchar(256) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `main_picture` varchar(40) DEFAULT NULL,
  `pictures` varchar(200) DEFAULT NULL,
  `is_new` tinyint(1) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcements`
--

LOCK TABLES `announcements` WRITE;
/*!40000 ALTER TABLE `announcements` DISABLE KEYS */;
INSERT INTO `announcements` VALUES (1,1,'Казань','Велосипеды','Велосипед Аист дорожный 111-353 (колеса 28\") Aist, синий','Надежный велосипед с привлекательным дизайном.\r\nВысочайшее качество компонентов в совокупности, прочная паянная рама.\r\nПрактичный, прочный багажник.\r\nОптимизированная под комфортную, расслабленную посадку закрытая рама позволяет часами ездить себе в удовольствие.\r\nОборудован удобным эргономичным сиденьем и обладает мягким ходом. Выгодно объединяет в себе новые технологии и уличный стиль.',13900,'19e4924ad935f7fcca3538c8984c65ee.jpg','58260fec78ee9c279b5abe63c5a3f1bc.jpg;',1,'2020-11-08 11:03:06'),(2,1,'Уфа','Велосипеды',' Велосипед дореволюционный','Велосипед с большим передним колесом',500000,'fece45dad22a79292a6b95401086489b.jpg','',0,'2020-11-08 11:05:23'),(3,1,'Казань','Велосипеды','Велосипеды с людьми','Быть здоровым, стройным и счастливым просто, когда есть двухколесный друг. \r\nНачните свой день с велосипедной прогулки и откройте для себя новые возможности.   \r\nИзо дня в день мы наблюдаем скопление велосипедистов на улицах города. И не только тинэйджеры пристрастились к этому виду транспорта. Все чаще мы замечаем и возрастных товарищей на колесах, прогуливающихся в парке или куда-то едущих по улицам города.',5600,'2233da9ecf478bebf7e297e5081f6012.jpg','d87f5ef0dccc0fa94e3bd62a75ee3a2b.jpg;',1,'2020-11-08 11:12:22'),(4,1,'Москва','Бытовая техника','Холодильник BEKO RCNK310KC0S, двухкамерный, серебристый','Холодильник BEKO RCNK310KC0S будет верно заботиться о сохранности доверенных продуктов и делать все, для экономии денег. Даже такая мелочь, как сигнал незакрытых дверей, в перспективе, сможет помочь сберечь немного энергии и уберечь от порчи продукты. Антибактериальное покрытие уплотнителя замедляет развитие микробов и плесени на продуктах. Даже без электричества холодильник сбережет содержимое до 18 часов.\r\n\r\nВ основную камеру помещается до 198 литров. Объем морозилки — 78 литров. Система NoFrost убережет от образования наледи и инея на стенках обеих камер холодильника и продуктах. За год устройству требуется 281 кВт/ч электричества — даже обычная лампочка потребляет больше.',30000,'b66108db7ed5fa1f8da386e5a9bd199a.jpg','93c0cc74a99e08b91e3fa651228f86f0.jpg;',1,'2020-11-08 11:09:46'),(5,1,'Санкт-Петербург','Бытовая техника','Газовая плита GEFEST ПГ 3200-08 К19, газовая духовка','Простая в использовании современная газовая плита ГЕФЕСТ ПГ 3200-08 K19 придется по нраву многим покупателям благодаря своим прекрасным техническим характеристикам. Данный бытовой прибор имеет ширину всего 50 см, поэтому его можно поместить даже в маленькой кухне. Варочная панель покрыта прочной эмалью, которая легко чистится любыми моющими средствами и прослужит вам не один год. Панель оснащена 4 конфорками разного диаметра и мощности. Плита имеет механическое управление, осуществляющееся с помощью поворотных регуляторов. Дверца духовки, которой оснащена газовая плита ГЕФЕСТ ПГ 3200-08 K19, оснащена термостойким двойным стеклом, которое сохраняет тепло во время процесса приготовления блюд. Внизу имеется удобный ящик, куда вы можете поместить сковородки и формы для выпечки. Духовка поможет приготовить ваши любимые блюда без всяких проблем.',500,'39aa3c8cfa8658baa10b883cf1fb0f3a.jpg','bf32518ef54ef3c426b9f1c8293ce888.jpg;',0,'2020-11-08 11:11:56'),(6,1,'Казань','Бытовая техника','Микроволновая Печь','Микроволновая печь BBK 20MWS-772M/S-M – прекрасный выбор для любой хозяйки. Отличается элегантным стильным дизайном, позволяющим органично вписать этот вид техники в любой кухонный интерьер. Выполнена из высококачественных прочных и безопасных материалов в приятном серебристом цвете. Характеризуется легким управлением, достаточной мощностью, вместительностью. Дверца СВЧ с зеркальной лицевой панелью и классические элементы управления позволяют сделать ее особенно привлекательной. При правильной эксплуатации станет верной помощницей на многие годы.\r\n\r\nВ микроволновой печи BBK 20MWS-772M/S-M представлены только самые необходимые функции для быстрого приготовления и подогрева пищи: объем внутренней камеры составляет 20 литров, таймер на 30 минут и максимальная мощность микроволнового излучения 700 Вт. О завершении операции оповестит звуковой сигнал.',1000,'35bc1a2fb8cae8ff02dbddfd649f7ccc.jpg','396c38628172988c56e3cf3ce1c182bd.jpg;f4e3adfd4fd6ec15916c294724093370.jpg;c44d298d3b3377e77d6988617cba6217.jpg;',0,'2020-11-08 11:14:22');
/*!40000 ALTER TABLE `announcements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tokens`
--

DROP TABLE IF EXISTS `tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tokens` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tokens`
--

LOCK TABLES `tokens` WRITE;
/*!40000 ALTER TABLE `tokens` DISABLE KEYS */;
/*!40000 ALTER TABLE `tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `surname` varchar(32) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `city` varchar(32) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `registered_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Иван','Иванов','test@gmail.com','Казань','$2a$10$HzBIVAQKPrgcppGKUudTduNfXglLDZWOMy5/nnBAuherxhP7.rm4S','2020-11-08 11:00:34');
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

-- Dump completed on 2020-11-08 17:24:36
