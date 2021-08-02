
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table imooc_mall_cart
# ------------------------------------------------------------

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车id',
  `product_id` int(11) NOT NULL COMMENT '商品id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `quantity` int(11) NOT NULL DEFAULT '1' COMMENT '商品数量',
  `selected` int(11) NOT NULL DEFAULT '1' COMMENT '是否已勾选：0代表未勾选，1代表已勾选',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='购物车';



# Dump of table imooc_mall_category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '分类目录名称',
  `type` int(11) NOT NULL COMMENT '分类目录级别，例如1代表一级，2代表二级，3代表三级',
  `parent_id` int(11) NOT NULL COMMENT '父id，也就是上一级目录的id，如果是一级目录，那么父id为0',
  `order_num` int(11) NOT NULL COMMENT '目录展示时的排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='商品分类 ';

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;

INSERT INTO `category` (`id`, `name`, `type`, `parent_id`, `order_num`, `create_time`, `update_time`)
VALUES
	(3,'新鲜水果',1,0,1,'2019-12-18 01:17:00','2019-12-28 17:11:26'),
	(4,'橘子橙子',2,3,1,'2019-12-18 01:17:00','2019-12-28 16:25:10'),


/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_no` varchar(128) NOT NULL DEFAULT '' COMMENT '订单号（非主键id）',
  `user_id` int(64) NOT NULL COMMENT '用户id',
  `total_price` int(64) NOT NULL COMMENT '订单总价格',
  `receiver_name` varchar(32) NOT NULL COMMENT '收货人姓名快照',
  `receiver_mobile` varchar(32) NOT NULL COMMENT '收货人手机号快照',
  `receiver_address` varchar(128) NOT NULL DEFAULT '' COMMENT '收货地址快照',
  `order_status` int(10) NOT NULL DEFAULT '10' COMMENT '订单状态: 0用户已取消，10未付款（初始状态），20已付款，30已发货，40交易完成',
  `postage` int(10) DEFAULT '0' COMMENT '运费，默认为0',
  `payment_type` int(4) NOT NULL DEFAULT '1' COMMENT '支付类型,1-在线支付',
  `delivery_time` timestamp NULL DEFAULT NULL COMMENT '发货时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '交易完成时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='订单表;';



# Dump of table item
# ------------------------------------------------------------

DROP TABLE IF EXISTS `order_item`;

CREATE TABLE `order_item` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_no` varchar(128) NOT NULL DEFAULT '' COMMENT '归属订单id',
  `product_id` int(11) NOT NULL COMMENT '商品id',
  `product_name` varchar(100) NOT NULL DEFAULT '' COMMENT '商品名称',
  `product_img` varchar(128) NOT NULL DEFAULT '' COMMENT '商品图片',
  `unit_price` int(11) NOT NULL COMMENT '单价（下单时的快照）',
  `quantity` int(10) NOT NULL DEFAULT '1' COMMENT '商品数量',
  `total_price` int(11) NOT NULL DEFAULT '0' COMMENT '商品总价',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='订单的商品表 ';



# Dump of table product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '商品主键id',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `image` varchar(500) NOT NULL DEFAULT '' COMMENT '产品图片,相对路径地址',
  `detail` varchar(500)  DEFAULT '' COMMENT '商品详情',
  `category_id` int(11) NOT NULL COMMENT '分类id',
  `price` int(11) NOT NULL COMMENT '价格,单位-分',
  `stock` int(11) NOT NULL COMMENT '库存数量',
  `status` int(6) NOT NULL DEFAULT '1' COMMENT '商品上架状态：0-下架，1-上架',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='商品表';

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;

INSERT INTO `product` (`id`, `name`, `detail`, `category_id`, `price`, `stock`, `status`, `create_time`, `update_time`)
VALUES
	(2,'澳洲进口大黑车厘子大樱桃包甜黑樱桃大果多汁 500g 特大果','商品毛重：1.0kg货号：608323093445原产地：智利类别：美早热卖时间：1月，11月，12月国产/进口：进口售卖方式：单品',14,50,1001,1,'2019-12-18 16:08:15','2020-03-07 18:44:26'),
	(3,'茶树菇 美味菌菇 东北山珍 500g','商品名：茶树菇 商品特点：美味菌菇 东北山珍 500g',15,1000,6,1,'2019-12-18 16:10:50','2020-03-07 18:44:26'),


/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table imooc_mall_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码，MD5加密',
  `personalized_signature` varchar(50) NOT NULL DEFAULT '' COMMENT '个性签名',
  `role` int(4) NOT NULL DEFAULT '1' COMMENT '角色，1-普通用户，2-管理员',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='用户表 ';

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `username`, `password`, `personalized_signature`, `role`, `create_time`, `update_time`)
VALUES
	(1,'1','1','666',1,'2019-12-16 02:37:33','2020-02-29 14:33:23'),
	(2,'xiaomu','AWRuqaxc6iryhHuA4OnFag==','更新了我的签名',2,'2019-12-17 15:11:32','2020-02-10 09:52:12'),
	(9,'xiaomu2','AWRuqaxc6iryhHuA4OnFag==','祝你今天好心情',2,'2020-02-09 20:39:47','2020-02-11 00:56:02'),
	(10,'mumu','12345678','',1,'2020-02-29 16:53:22','2020-02-29 16:53:22'),
	(11,'mumu3','124567474','',1,'2020-02-29 16:56:07','2020-02-29 16:56:07'),
	(12,'mumu4','SMRMN9k6YmXAjbsJCMdxrQ==','天气晴朗',1,'2020-02-29 17:25:55','2020-02-29 21:59:02'),
	(13,'mumu5','SMRMN9k6YmXAjbsJCMdxrQ==','奋勇向前',2,'2020-02-29 22:09:56','2020-02-29 22:12:11'),
	(14,'imooc','SMRMN9k6YmXAjbsJCMdxrQ==','',1,'2020-03-02 22:45:48','2020-03-02 22:45:48'),
	(15,'super2','SMRMN9k6YmXAjbsJCMdxrQ==','',1,'2020-03-07 18:09:47','2020-03-07 18:09:47');

/*!40000 ALTER TABLE `imooc_mall_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
