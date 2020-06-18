-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 47.101.183.63    Database: cinema
-- ------------------------------------------------------
-- Server version	5.7.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--
SET @@session.sql_mode ='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';


DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(45) NOT NULL,
  `a_description` varchar(255) NOT NULL,
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `coupon_id` int(11) DEFAULT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (2,'春季外卖节','春季外卖节','2019-04-23 17:55:59',5,'2019-04-20 17:55:59'),(3,'春季外卖节','春季外卖节','2019-04-23 17:55:59',6,'2019-04-20 17:55:59'),(4,'测试活动','测试活动','2019-04-26 16:00:00',8,'2019-04-20 16:00:00');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_movie`
--

DROP TABLE IF EXISTS `activity_movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_movie` (
  `activity_id` int(11) DEFAULT NULL,
  `movie_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_movie`
--

LOCK TABLES `activity_movie` WRITE;
/*!40000 ALTER TABLE `activity_movie` DISABLE KEYS */;
INSERT INTO `activity_movie` VALUES (2,10),(2,11),(2,16),(4,10);
/*!40000 ALTER TABLE `activity_movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `target_amount` float DEFAULT NULL,
  `discount_amount` float DEFAULT NULL,
  `start_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (1,'测试优惠券','春季电影节',20,5,'2019-04-20 17:47:54','2019-04-23 17:47:59'),(5,'测试优惠券','品质联盟',30,4,'2019-04-20 21:14:46','2019-04-24 21:14:51'),(6,'春节电影节优惠券','电影节优惠券',50,10,'2019-04-20 21:15:11','2019-04-21 21:14:56'),(8,'测试优惠券','123',100,99,'2019-04-20 16:00:00','2019-04-26 16:00:00');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon_user`
--

DROP TABLE IF EXISTS `coupon_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon_user` (
  `coupon_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon_user`
--

LOCK TABLES `coupon_user` WRITE;
/*!40000 ALTER TABLE `coupon_user` DISABLE KEYS */;
INSERT INTO `coupon_user` VALUES (8,15),(5,15),(8,15),(6,15),(5,15),(8,15),(6,15);
/*!40000 ALTER TABLE `coupon_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hall`
--

DROP TABLE IF EXISTS `hall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hall` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `column` int(11) DEFAULT NULL,
  `row` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hall`
--

LOCK TABLES `hall` WRITE;
/*!40000 ALTER TABLE `hall` DISABLE KEYS */;
INSERT INTO `hall` VALUES (1,'1号厅',10,5),(2,'2号厅',12,8);
/*!40000 ALTER TABLE `hall` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poster_url` varchar(255) DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `screen_writer` varchar(255) DEFAULT NULL,
  `starring` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `length` int(11) NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) NOT NULL,
  `description` text,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (10,'http://n.sinaimg.cn/translate/640/w600h840/20190312/ampL-hufnxfm4278816.jpg','大森贵弘 /伊藤秀樹','','神谷浩史 /井上和彦 /高良健吾 /小林沙苗 /泽城美雪','动画',NULL,NULL,120,'2019-04-14 14:54:31','夏目友人帐','在人与妖怪之间过着忙碌日子的夏目，偶然与以前的同学结城重逢，由此回忆起了被妖怪缠身的苦涩记忆。此时，夏目认识了在归还名字的妖怪记忆中出现的女性·津村容莉枝。和玲子相识的她，现在和独子椋雄一同过着平稳的生活。夏目通过与他们的交流，心境也变得平和。但这对母子居住的城镇，却似乎潜伏着神秘的妖怪。在调查此事归来后，寄生于猫咪老师身体的“妖之种”，在藤原家的庭院中，一夜之间就长成树结出果实。而吃掉了与自己形状相似果实的猫咪老师，竟然分裂成了3个',0),(11,'','安娜·波顿',NULL,'布利·拉尔森','动作/冒险/科幻',NULL,NULL,120,'2019-04-16 14:55:31','惊奇队长','漫画中的初代惊奇女士曾经是一名美国空军均情报局探员，暗恋惊奇先生。。。',0),(12,'','1',NULL,'1','1',NULL,NULL,120,'2019-04-16 14:57:31','1','1',0),(13,'2','2',NULL,'2','2',NULL,NULL,120,'2019-04-16 14:52:31','2','2',0),(14,'','2',NULL,'2','2',NULL,NULL,120,'2019-04-18 13:23:15','2','2',1),(15,'1','1','1','1','1','1','1',111,'2019-04-16 15:00:24','nnmm,,,','1',0),(16,'https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2549523952.webp','林孝谦','abcˆ','陈意涵','爱情','大陆',NULL,123,'2019-04-18 13:23:15','比悲伤更悲伤的故事','唱片制作人张哲凯(刘以豪)和王牌作词人宋媛媛(陈意涵)相依为命，两人自幼身世坎坷只有彼此为伴，他们是亲人、是朋友，也彷佛是命中注定的另一半。父亲罹患遗传重症而被母亲抛弃的哲凯，深怕自己随时会发病不久人世，始终没有跨出友谊的界线对媛媛展露爱意。眼见哲凯的病情加重，他暗自决定用剩余的生命完成他们之间的终曲，再为媛媛找个可以托付一生的好男人。这时，事业有 成温柔体贴的医生(张书豪)适时的出现让他成为照顾媛媛的最佳人选，二人按部就班发展着关系。一切看似都在哲凯的计划下进行。然而，故事远比这里所写更要悲伤......',1);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_like`
--

DROP TABLE IF EXISTS `movie_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_like` (
  `movie_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `like_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`movie_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_like`
--

LOCK TABLES `movie_like` WRITE;
/*!40000 ALTER TABLE `movie_like` DISABLE KEYS */;
INSERT INTO `movie_like` VALUES (10,12,'2019-03-25 02:40:19'),(11,1,'2019-03-22 09:38:12'),(11,2,'2019-03-23 09:38:12'),(11,3,'2019-03-22 08:38:12'),(12,1,'2019-03-23 09:48:46'),(12,3,'2019-03-25 06:36:22'),(14,1,'2019-03-23 09:38:12'),(16,12,'2019-03-23 15:27:48');
/*!40000 ALTER TABLE `movie_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hall_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `start_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL,
  `fare` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (20,1,12,'2019-04-13 17:00:00','2019-04-13 18:00:00',20.5),(21,1,10,'2019-04-11 12:00:00','2019-04-11 13:00:00',90),(27,1,11,'2019-04-17 18:01:00','2019-04-17 20:01:00',20.5),(28,1,11,'2019-04-19 16:00:00','2019-04-19 18:00:00',20.5),(30,1,11,'2019-04-18 18:01:00','2019-04-18 20:01:00',20.5),(31,1,11,'2019-04-12 16:00:00','2019-04-12 18:00:00',20.5),(32,1,11,'2019-04-12 20:00:00','2019-04-12 22:00:00',20.5),(37,1,11,'2019-04-15 00:00:00','2019-04-15 02:00:00',20.5),(38,1,11,'2019-04-14 17:00:00','2019-04-14 19:00:00',20.5),(40,1,10,'2019-04-10 16:00:00','2019-04-10 18:00:00',20.5),(41,1,11,'2019-04-10 19:00:00','2019-04-10 21:00:00',20.5),(42,1,11,'2019-04-10 22:00:00','2019-04-11 00:00:00',20.5),(43,1,10,'2019-04-11 01:00:00','2019-04-11 03:00:00',20.5),(44,2,10,'2019-04-11 01:00:00','2019-04-11 03:00:00',20.5),(45,2,10,'2019-04-10 22:00:00','2019-04-11 00:00:00',20.5),(46,2,11,'2019-04-10 19:00:00','2019-04-10 21:00:00',20.5),(47,2,11,'2019-04-10 16:00:00','2019-04-10 18:00:00',20.5),(48,2,10,'2019-04-11 13:00:00','2019-04-11 15:59:00',20.5),(50,1,10,'2019-04-15 16:00:00','2019-04-15 19:00:00',2),(51,1,10,'2019-04-17 05:00:00','2019-04-17 07:00:00',9),(52,1,10,'2019-04-18 05:00:00','2019-04-18 07:00:00',9),(53,1,16,'2019-04-19 07:00:00','2019-04-19 10:00:00',9),(54,1,16,'2019-04-16 19:00:00','2019-04-16 22:00:00',9),(55,1,15,'2019-04-17 23:00:00','2019-04-18 01:00:00',9),(56,2,10,'2019-04-19 13:00:00','2019-04-19 15:59:00',20.5),(57,2,10,'2019-04-20 13:00:00','2019-04-20 15:59:00',20.5),(58,2,10,'2019-04-21 13:00:00','2019-04-21 15:59:00',20.5),(61,1,13,'2019-04-20 11:00:00','2019-04-20 13:00:00',25),(62,1,11,'2019-04-20 08:00:00','2019-04-20 10:00:00',25),(63,2,15,'2019-04-20 16:01:30','2019-04-21 05:30:00',30),(64,1,16,'2019-04-22 02:00:00','2019-04-22 05:30:00',30),(65,1,10,'2019-04-23 02:00:00','2019-04-23 05:30:00',30),(66,2,13,'2019-04-21 07:31:29','2019-04-16 15:59:00',20.5),(67,2,10,'2019-04-25 13:00:00','2019-04-25 15:59:00',20.5),(68,2,10,'2019-04-26 13:00:00','2019-04-26 15:59:00',20.5);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `user_id` int(11) DEFAULT NULL,
  `schedule_id` int(11) DEFAULT NULL,
  `column_index` int(11) DEFAULT NULL,
  `row_index` int(11) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;

/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_uindex` (`id`),
  UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'testname','123456'),(5,'test1','123456'),(7,'test121','123456'),(8,'root','123456'),(10,'roottt','123123'),(12,'zhourui','123456'),(13,'abc123','abc123'),(15,'dd','123');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `worker`
--

DROP TABLE IF EXISTS `worker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `worker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workername` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `worker_id_uindex` (`id`),
  UNIQUE KEY `worker_workername_uindex` (`workername`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `worker`
--

LOCK TABLES `worker` WRITE;
/*!40000 ALTER TABLE `worker` DISABLE KEYS */;
INSERT INTO `worker` VALUES (1,'test','123456');
/*!40000 ALTER TABLE `worker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `view`
--

DROP TABLE IF EXISTS `view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `view` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `view`
--

LOCK TABLES `view` WRITE;
/*!40000 ALTER TABLE `view` DISABLE KEYS */;
INSERT INTO `view` VALUES (1,7);
/*!40000 ALTER TABLE `view` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip_card`
--

DROP TABLE IF EXISTS `vip_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vip_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kind` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `description` text,
  `money1` float DEFAULT NULL,
  `money2` float DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `balance` float DEFAULT NULL,
  `join_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `charge_history` varchar(1023) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `vip_card_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_card`
--

LOCK TABLES `vip_card` WRITE;
/*!40000 ALTER TABLE `vip_card` DISABLE KEYS */;
INSERT INTO `vip_card` VALUES (1,1,'白金会员卡',25,'满200送50',200,50,15,375,'2019-04-21 13:54:38',null),(2,2,'黑金会员卡',80,'满400送200',400,200,12,660,'2019-04-17 18:47:42',null);
/*!40000 ALTER TABLE `vip_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip_info`
--

DROP TABLE IF EXISTS `vip_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vip_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `description` text,
  `money1` float DEFAULT NULL,
  `money2` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `vip_info_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_info`
--

LOCK TABLES `vip_info` WRITE;
/*!40000 ALTER TABLE `vip_info` DISABLE KEYS */;
INSERT INTO `vip_info` VALUES (1,'白金会员卡',25,'满200送50',200,50),(2,'黑金会员卡',80,'满400送200',400,200);
/*!40000 ALTER TABLE `vip_info` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `refund`
--

DROP TABLE IF EXISTS `refund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refund` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time_one` int(11) NOT NULL,
  `time_two` int(11) NOT NULL,
  `deadline` int(11) NOT NULL,
  `time_one_percent` double NOT NULL,
  `time_two_percent` double NOT NULL,
  `deadline_percent` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `refund`
--

LOCK TABLES `refund` WRITE;
/*!40000 ALTER TABLE `vip_card` ENABLE KEYS */;

UNLOCK TABLES;


--
-- Table structure for table `orderForm_ticket`
--

DROP TABLE IF EXISTS `orderForm_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderForm_ticket` (
  `order_form_id` int(11) DEFAULT NULL ,
  `ticket_id` int(11) DEFAULT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Dumping data for table `orderForm_ticket`
--
LOCK TABLES `orderForm_ticket` WRITE;
/*!40000 ALTER TABLE `orderForm_ticket` DISABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `orderForm`
--

DROP TABLE IF EXISTS `orderForm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderForm` (
  `user_id` int(11) DEFAULT NULL,
  `coupon_id` int(11) DEFAULT NULL,
  `order_form_id` int(11) NOT NULL AUTO_INCREMENT,
  `pay_path` tinyint(4) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_form_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderForm`
--

LOCK TABLES `orderForm` WRITE;
/*!40000 ALTER TABLE `orderForm` DISABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scrollbar`
--

DROP TABLE IF EXISTS `scrollbar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scrollbar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `picture_url` varchar(255) NOT NULL,
  `web_url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `scrollbar`
--

LOCK TABLES `scrollbar` WRITE;
/*!40000 ALTER TABLE `vip_card` ENABLE KEYS */;
INSERT INTO `scrollbar` VALUES (1,'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560179934921&di=4f2c75ceddb21240670213f9576a4746&imgtype=0&src=http%3A%2F%2Fzkres1.myzaker.com%2F201905%2F5ce36d3177ac642f8e7f6fe4_1024.jpg','http://preview.ewang.com/movie/videos/854x480b598001693d047dd89c1174a9ff86664.mp4'),
(2,'http://img4.imgtn.bdimg.com/it/u=787029033,2207185519&fm=11&gp=0.jpg','哥斯拉2'),
(3,'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2688374484,1033678278&fm=26&gp=0.jpg','波西米亚狂想曲'),
(4,'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1016096020,2274320137&fm=26&gp=0.jpg','蜘蛛侠英雄归来'),
(5,'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560180899023&di=cc927fcc769e09872120a5d58f015084&imgtype=0&src=http%3A%2F%2F2a.zol-img.com.cn%2Fproduct%2F92_501x2000%2F434%2FcewUTvN0A5AM.jpg','黑暗骑士'),
(6,'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560181036282&di=7eed9b62eaa862a667ec8d2abbcff87e&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn10118%2F496%2Fw829h467%2F20190217%2F0e8c-htacqww2577276.jpg','流浪地球');
/*!40000 ALTER TABLE `vip_card` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `content`varchar(1023) DEFAULT NULL,
  `time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `movie_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
/*INSERT INTO `coupon` VALUES (1,'测试优惠券','春季电影节',20,5,'2019-04-20 17:47:54','2019-04-23 17:47:59'),(5,'测试优惠券','品质联盟',30,4,'2019-04-20 21:14:46','2019-04-24 21:14:51'),(6,'春节电影节优惠券','电影节优惠券',50,10,'2019-04-20 21:15:11','2019-04-21 21:14:56'),(8,'测试优惠券','123',100,99,'2019-04-20 16:00:00','2019-04-26 16:00:00');*/
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping events for database 'cinema'
--

--
-- Dumping routines for database 'cinema'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-24 21:20:52