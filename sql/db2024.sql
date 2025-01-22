/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 9.0.0 : Database - db2024
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db2024` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `db2024`;

/*Table structure for table `t_pay` */

DROP TABLE IF EXISTS `t_pay`;

CREATE TABLE `t_pay` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `pay_no` varchar(50) NOT NULL COMMENT '支付流水号',
  `order_no` varchar(50) NOT NULL COMMENT '订单流水号',
  `user_id` int DEFAULT '1' COMMENT '用户账号ID',
  `amount` decimal(8,2) NOT NULL DEFAULT '9.90' COMMENT '交易金额',
  `deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除标志，默认0不删除，1删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付交易表';

/*Data for the table `t_pay` */

insert  into `t_pay`(`id`,`pay_no`,`order_no`,`user_id`,`amount`,`deleted`,`create_time`,`update_time`) values 
(1,'pay21720407601','rrrrr01',11,219.90,0,'2024-07-22 17:50:00','2024-07-23 16:05:01'),
(7,'payFeign17204076','consumer-feign1c424',2,8.90,0,'2024-07-30 14:55:26','2024-07-30 14:55:26');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
