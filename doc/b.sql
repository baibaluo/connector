
USE `tb_shop`;

/*Table structure for table `dist_item_info` */


/*Table structure for table `dist_item_pic` */

DROP TABLE IF EXISTS `dist_item_pic`;

CREATE TABLE `dist_item_pic` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `s_item_id` bigint(20) default NULL,
  `s_nick` varchar(128) default NULL,
  `s_pic_id` varchar(64) default NULL,
  `s_pic_url` varchar(1024) default NULL,
  `s_pic_position` varchar(16) default NULL,
  `s_pic_prop` varchar(64) default NULL COMMENT '属性图片中用到',
  `s_pic_flag` tinyint(4) default NULL COMMENT '0为主图，1为desc，2为prop图',
  `s_pic_local_name` varchar(256) default NULL COMMENT '存储在本地的图片名字',
  `flag` tinyint(4) default NULL COMMENT '-1缺省值，0替换成功，1下载成功未上传',
  `newflag` tinyint(4) default '1' COMMENT '1新图片，0为已经处理完的图片',
  `downflag` tinyint(4) default '1' COMMENT '1未下载完，0已经抓取到本地',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=163276 DEFAULT CHARSET=utf8;

/*Table structure for table `email_account` */

DROP TABLE IF EXISTS `email_account`;

CREATE TABLE `email_account` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `email_account` varchar(128) default NULL,
  `passwd` varchar(128) default NULL,
  `domain` varchar(128) default NULL,
  `flag` tinyint(4) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=80 DEFAULT CHARSET=latin1;

/*Table structure for table `itemrelate` */

DROP TABLE IF EXISTS `itemrelate`;

CREATE TABLE `itemrelate` (
  `id` bigint(20) NOT NULL auto_increment,
  `agent_item_id` bigint(20) default NULL,
  `distrib_item_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=128 DEFAULT CHARSET=latin1;

/*Table structure for table `jd_orders` */

DROP TABLE IF EXISTS `jd_orders`;

CREATE TABLE `jd_orders` (
  `order_id` varchar(10) default NULL,
  `vender_id` varchar(10) default NULL,
  `pay_type` varchar(30) default NULL,
  `freight_price` varchar(20) default NULL,
  `ware_total_price` varchar(20) default NULL,
  `order_total_price` varchar(20) default NULL,
  `payment` varchar(20) default NULL,
  `order_state` varchar(10) default NULL,
  `fact_freight_price` varchar(20) default NULL,
  `delivery_date_remark` varchar(20) default NULL,
  `total_discount_fee` varchar(10) default NULL,
  `buyer_order_remark` varchar(200) default NULL,
  `seller_order_remark` varchar(200) default NULL,
  `order_start_time` varchar(20) default NULL,
  `order_end_time` varchar(20) default NULL,
  `invoice_info` varchar(100) default NULL,
  `oid` int(11) NOT NULL auto_increment,
  UNIQUE KEY `oid` (`oid`)
) ENGINE=MyISAM AUTO_INCREMENT=2905 DEFAULT CHARSET=utf8;

/*Table structure for table `jd_ware_infos` */

DROP TABLE IF EXISTS `jd_ware_infos`;

CREATE TABLE `jd_ware_infos` (
  `ware_id` varchar(20) default NULL,
  `ware_out_id` varchar(20) default NULL,
  `ware_name` varchar(80) default NULL,
  `sku_out_id` varchar(20) default NULL,
  `sku_id` varchar(20) default NULL,
  `jd_price` varchar(10) default NULL,
  `ware_total` varchar(16) default NULL,
  `product_no` varchar(10) default NULL,
  `ware_discount_fee` varchar(20) default NULL,
  `gift_point` varchar(16) default NULL,
  `ware_infos_id` varchar(10) default NULL,
  `user_name` varchar(18) default NULL,
  `user_address` varchar(100) default NULL,
  `user_post` varchar(16) default NULL,
  `user_telephone` varchar(20) default NULL,
  `user_mobilephone` varchar(20) default NULL,
  `user_email` varchar(36) default NULL,
  `order_id` varchar(10) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `picrelate` */

DROP TABLE IF EXISTS `picrelate`;

CREATE TABLE `picrelate` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `s_item_id` bigint(20) default NULL,
  `s_nick` varchar(64) default NULL,
  `s_pic_id` varchar(64) default NULL,
  `s_pic_url` varchar(1024) default NULL,
  `s_pic_position` varchar(16) default NULL,
  `s_pic_prop` varchar(64) default NULL COMMENT '属性图片中用到',
  `s_pic_flag` tinyint(4) default NULL COMMENT '0为主图，1为desc，2为prop图',
  `s_pic_local_name` varchar(256) default NULL COMMENT '存储在本地的图片名字',
  `t_item_id` bigint(20) default NULL,
  `t_nick` varchar(64) default NULL,
  `t_pic_id` varchar(64) default NULL,
  `t_item_pic_url` varchar(1024) default NULL,
  `t_pic_position` varchar(16) default NULL,
  `t_pic_title` varchar(32) default NULL,
  `t_pic_cat_id` bigint(20) default NULL,
  `t_pixel` varchar(32) default NULL,
  `t_size` varchar(16) default NULL,
  `flag` tinyint(4) default NULL COMMENT '-1缺省值，0替换成功，1下载成功未上传',
  `newflag` tinyint(4) default '-1',
  `downflag` tinyint(4) default '-1',
  `upflag` tinyint(4) default '-1',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=110266 DEFAULT CHARSET=utf8;

/*Table structure for table `picrelate_bak` */

DROP TABLE IF EXISTS `picrelate_bak`;

CREATE TABLE `picrelate_bak` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `s_item_id` bigint(20) default NULL,
  `s_nick` varchar(128) default NULL,
  `s_pic_id` varchar(64) default NULL,
  `s_pic_url` varchar(1024) default NULL,
  `s_pic_position` varchar(16) default NULL,
  `s_pic_prop` varchar(64) default NULL COMMENT '属性图片中用到',
  `s_pic_flag` tinyint(4) default NULL COMMENT '0为主图，1为desc，2为prop图',
  `s_pic_local_name` varchar(256) default NULL COMMENT '存储在本地的图片名字',
  `t_item_id` bigint(20) default NULL,
  `t_pic_id` varchar(64) default NULL,
  `t_item_pic_url` varchar(1024) default NULL,
  `t_pic_position` varchar(16) default NULL,
  `t_pic_title` varchar(32) default NULL,
  `t_pic_cat_id` bigint(20) default NULL,
  `t_pixel` varchar(32) default NULL,
  `t_size` varchar(16) default NULL,
  `flag` tinyint(4) default NULL COMMENT '-1缺省值，0替换成功，1下载成功未上传',
  `newflag` tinyint(4) default '1' COMMENT '1新图片，0为已经处理完的图片',
  `downflag` tinyint(4) default '1' COMMENT '1未下载完，0已经抓取到本地',
  `upflag` tinyint(4) default '1' COMMENT '1未上传完，0已经上传完成',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=99971 DEFAULT CHARSET=utf8;

/*Table structure for table `prorelate` */

DROP TABLE IF EXISTS `prorelate`;

CREATE TABLE `prorelate` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `att_id` bigint(20) default NULL,
  `att_name` varchar(32) default NULL,
  `spec_value_id` bigint(20) default NULL,
  `spec_value` varchar(32) default NULL,
  `spec_alia_value` varchar(32) default NULL,
  `spec_status` tinyint(4) default NULL,
  `spec_flag` tinyint(4) default NULL,
  `t_attkey` bigint(20) default NULL,
  `t_att_name` varchar(32) default NULL,
  `t_spec_value_id` bigint(20) default NULL,
  `t_spec_value` varchar(32) default NULL,
  `t_spec_alia_value` varchar(32) default NULL,
  `t_spec_stuts` tinyint(4) default NULL,
  `flag` tinyint(4) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=162 DEFAULT CHARSET=latin1;

/*Table structure for table `seller_prd_title` */

DROP TABLE IF EXISTS `seller_prd_title`;

CREATE TABLE `seller_prd_title` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `title` varchar(256) character set utf8 default NULL,
  `nick` varchar(64) character set utf8 default NULL,
  `num_iid` bigint(20) default NULL,
  `flag` tinyint(4) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=940 DEFAULT CHARSET=gbk CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Table structure for table `shop_pic` */

DROP TABLE IF EXISTS `shop_pic`;

CREATE TABLE `shop_pic` (
  `prd_id` bigint(20) unsigned NOT NULL,
  `s_tb_url` varchar(255) NOT NULL default '""',
  `local_name` varchar(255) NOT NULL default '""',
  `t_tb_url` varchar(255) NOT NULL default '""',
  `flag` tinyint(2) default '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `shopcat` */

DROP TABLE IF EXISTS `shopcat`;

CREATE TABLE `shopcat` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `s_cid` varchar(32) default NULL,
  `s_name` varchar(32) default NULL,
  `s_parent_cid` varchar(32) default NULL,
  `s_pic_url` varchar(256) default NULL,
  `s_sort_order` tinyint(4) default NULL,
  `s_type` varchar(128) default NULL,
  `s_nick` varchar(64) default NULL,
  `t_cid` varchar(32) default NULL,
  `t_name` varchar(32) default NULL,
  `t_parent_cid` varchar(32) default NULL,
  `t_pic_url` varchar(256) default NULL,
  `t_sort_order` tinyint(4) default NULL,
  `t_type` varchar(16) default NULL,
  `t_nick` varchar(128) default NULL,
  `flag` tinyint(4) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=135 DEFAULT CHARSET=latin1;

/*Table structure for table `skurelate` */

DROP TABLE IF EXISTS `skurelate`;

CREATE TABLE `skurelate` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `num_iid` bigint(20) default NULL,
  `s_sku_id` bigint(20) default NULL,
  `s_sku_prop` varchar(128) default NULL,
  `s_sku_out_id` bigint(20) default NULL,
  `s_sku_color` varchar(64) default NULL,
  `s_sku_color_val` varchar(32) default NULL,
  `s_sku_size` varchar(64) default NULL,
  `s_sku_size_val` varchar(64) default NULL,
  `s_sku_price` varchar(32) default NULL,
  `s_sku_num` int(11) default NULL,
  `s_sku_mod_time` varchar(32) default NULL,
  `s_prop_alia` varchar(64) default NULL,
  `t_num_iid` bigint(20) default NULL,
  `t_sku_id` bigint(20) default NULL,
  `t_sku_prop` varchar(128) default NULL,
  `t_sku_out_id` bigint(20) default NULL,
  `t_sku_color` varchar(64) default NULL,
  `t_sku_color_val` varchar(32) default NULL,
  `t_sku_size` varchar(64) default NULL,
  `t_sku_size_val` varchar(32) default NULL,
  `t_sku_price` varchar(32) default NULL,
  `t_sku_num` int(11) default NULL,
  `t_sku_mod_time` varchar(32) default NULL,
  `t_prop_alia` varchar(64) default NULL,
  `flag` tinyint(4) default '-1' COMMENT '-1æ–°çš„skuæœªå¤„ç†ï¼Œ1æœ‰updateéœ€è¦å¤„ç†ï¼Œ0å¤„ç†å®Œæ¯•ï¼Œ2å·²ç»ä¸‹æž¶',
  `aliaflag` tinyint(4) default '-1' COMMENT '-1ç¼ºçœå€¼,3ã€å±žæ€§åæœ‰æ‰€å˜åŒ–éœ€è¦å¤„ç†ã€‚',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2095 DEFAULT CHARSET=latin1;


/*Table structure for table `sup_item_pic` */

DROP TABLE IF EXISTS `sup_item_pic`;

CREATE TABLE `sup_item_pic` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `s_item_id` bigint(20) default NULL,
  `s_nick` varchar(128) default NULL,
  `s_pic_id` varchar(64) default NULL,
  `s_pic_url` varchar(1024) default NULL,
  `s_pic_position` varchar(16) default NULL,
  `s_pic_prop` varchar(64) default NULL COMMENT '属性图片中用到',
  `s_pic_flag` tinyint(4) default NULL COMMENT '0为主图，1为desc，2为prop图',
  `s_pic_local_name` varchar(256) default NULL COMMENT '存储在本地的图片名字',
  `flag` tinyint(4) default NULL COMMENT '-1缺省值，0替换成功，1下载成功未上传',
  `newflag` tinyint(4) default '1' COMMENT '1新图片，0为已经处理完的图片',
  `downflag` tinyint(4) default '1' COMMENT '1未下载完，0已经抓取到本地',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=163276 DEFAULT CHARSET=utf8;

/*Table structure for table `supply` */

DROP TABLE IF EXISTS `supply`;

CREATE TABLE `supply` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `sup_name` varchar(128) NOT NULL,
  `sup_att` int(11) default '0' COMMENT '1C店，2商城，3还没有',
  `wait_time` bigint(20) default '10800' COMMENT '缺省3个小时',
  `flag` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `supply_dist` */

DROP TABLE IF EXISTS `supply_dist`;

CREATE TABLE `supply_dist` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `sup_name` varchar(128) NOT NULL,
  `sup_att` int(11) default '0' COMMENT '1C店，2商城，3还没有',
  `wait_time` bigint(20) default '10800' COMMENT '缺省3个小时',
  `dist_name` varchar(128) default NULL,
  `dist_att` int(11) default '0' COMMENT '1C店，2商城，3还没有京东或者其他等等另定义',
  `dist_appkey` varchar(64) default NULL,
  `dist_secretkey` varchar(128) default NULL,
  `dist_shortname` varchar(32) default NULL,
  `flag` int(11) default NULL COMMENT '0可以进行同步（SUP和DIST都有）,1暂存储,2其他情况',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Table structure for table `tuser` */

DROP TABLE IF EXISTS `tuser`;

CREATE TABLE `tuser` (
  `t_id` int(10) NOT NULL auto_increment COMMENT '用户ID，自增长',
  `t_username` varchar(30) NOT NULL,
  `t_password` varchar(40) NOT NULL COMMENT '用户密码md5加密',
  `t_truename` varchar(30) NOT NULL COMMENT '教师姓名（中文）',
  `t_idcard` varchar(20) NOT NULL COMMENT '身份证',
  `t_email` varchar(30) NOT NULL COMMENT '邮箱',
  `t_date` datetime NOT NULL COMMENT '注册时间',
  `t_status` tinyint(4) NOT NULL default '1' COMMENT '用户状态 0 删除,1 正常',
  `t_infostatus` tinyint(4) NOT NULL default '1' COMMENT '用户信息提交状态 0 未提交,1 已提交',
  `s_id` int(8) NOT NULL COMMENT '院校ID',
  PRIMARY KEY  (`t_id`),
  UNIQUE KEY `t_username` (`t_username`),
  KEY `t_status_idx` (`t_status`),
  KEY `s_id_tuser_idx` (`s_id`),
  KEY `t_truename_idx` (`t_truename`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Table structure for table `wordstat` */

DROP TABLE IF EXISTS `wordstat`;

CREATE TABLE `wordstat` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `word` varchar(32) character set utf8 default NULL,
  `pr` double(12,10) default '0.0000000000',
  `num` bigint(20) default '0',
  `order_num` bigint(20) default '0',
  `back` varchar(32) character set utf8 default NULL,
  `flag` tinyint(4) default '-1',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4538 DEFAULT CHARSET=gbk CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
