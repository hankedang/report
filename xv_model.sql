/*
Navicat MySQL Data Transfer

Source Server         : 本地测试
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : hkd

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2016-07-27 12:48:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for xv_chart
-- ----------------------------
DROP TABLE IF EXISTS `xv_chart`;
CREATE TABLE `xv_chart` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `type` varchar(16) NOT NULL DEFAULT 'line',
  `title` varchar(128) DEFAULT NULL,
  `sub_title` varchar(120) DEFAULT NULL,
  `description` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_chart
-- ----------------------------

-- ----------------------------
-- Table structure for xv_chart_axis
-- ----------------------------
DROP TABLE IF EXISTS `xv_chart_axis`;
CREATE TABLE `xv_chart_axis` (
  `chart_id` int(8) NOT NULL,
  `chart_axis_col` varchar(128) NOT NULL,
  `chart_axis_flag` varchar(128) NOT NULL,
  `chart_axis_code` int(8) NOT NULL COMMENT 'col order in axis',
  `chart_axis_name` varchar(128) DEFAULT NULL COMMENT 'axis name'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_chart_axis
-- ----------------------------

-- ----------------------------
-- Table structure for xv_chart_style
-- ----------------------------
DROP TABLE IF EXISTS `xv_chart_style`;
CREATE TABLE `xv_chart_style` (
  `id` int(8) NOT NULL,
  `chart_id` int(8) NOT NULL,
  `fontFamily` varchar(32) DEFAULT NULL,
  `fontSize` int(4) DEFAULT NULL,
  `fontWeight` varchar(16) DEFAULT NULL,
  `color` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_chart_style
-- ----------------------------

-- ----------------------------
-- Table structure for xv_dashboard
-- ----------------------------
DROP TABLE IF EXISTS `xv_dashboard`;
CREATE TABLE `xv_dashboard` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `dim_id` int(4) DEFAULT NULL,
  `ex_value` varchar(16) DEFAULT NULL,
  `board` varchar(32) DEFAULT NULL,
  `datespan` varchar(16) DEFAULT 'before',
  `cycle` int(2) DEFAULT 0 COMMENT '0: 单表， 1：年表，2：月表，3：天表',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_dashboard
-- ----------------------------
INSERT INTO `xv_dashboard` VALUES ('1', 'platform', '101', null, null, 'before', 0);


-- ----------------------------
-- Table structure for xv_dashboard_column
-- ----------------------------
DROP TABLE IF EXISTS `xv_dashboard_column`;
CREATE TABLE `xv_dashboard_column` (
  `theme_id` int(4) NOT NULL,
  `col_id` int(4) NOT NULL,
  `flag` int(2) NOT NULL DEFAULT '1',
  `defvalue` varchar(32) DEFAULT NULL,
  `level` int(2) NOT NULL DEFAULT '1',
  `code` int(2) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_dashboard_column
-- ----------------------------
INSERT INTO `xv_dashboard_column` VALUES ('1', '101', '1', null, '1', '1');
INSERT INTO `xv_dashboard_column` VALUES ('1', '102', '1', null, '1', '2');
INSERT INTO `xv_dashboard_column` VALUES ('1', '103', '1', null, '1', '3');
INSERT INTO `xv_dashboard_column` VALUES ('1', '104', '1', null, '1', '4');
INSERT INTO `xv_dashboard_column` VALUES ('1', '105', '1', null, '1', '5');
INSERT INTO `xv_dashboard_column` VALUES ('1', '106', '1', null, '1', '6');
INSERT INTO `xv_dashboard_column` VALUES ('1', '107', '1', null, '1', '7');
INSERT INTO `xv_dashboard_column` VALUES ('1', '108', '1', null, '1', '8');
INSERT INTO `xv_dashboard_column` VALUES ('1', '109', '1', null, '1', '9');
INSERT INTO `xv_dashboard_column` VALUES ('1', '110', '1', null, '1', '10');
INSERT INTO `xv_dashboard_column` VALUES ('1', '111', '1', null, '1', '11');
INSERT INTO `xv_dashboard_column` VALUES ('1', '112', '1', null, '1', '12');
INSERT INTO `xv_dashboard_column` VALUES ('1', '113', '1', null, '1', '13');
INSERT INTO `xv_dashboard_column` VALUES ('1', '114', '1', null, '1', '14');
INSERT INTO `xv_dashboard_column` VALUES ('1', '115', '1', null, '1', '15');
INSERT INTO `xv_dashboard_column` VALUES ('1', '116', '1', null, '1', '16');
INSERT INTO `xv_dashboard_column` VALUES ('1', '117', '1', null, '1', '17');

-- ----------------------------
-- Table structure for xv_dashboard_drill
-- ----------------------------
DROP TABLE IF EXISTS `xv_dashboard_drill`;
CREATE TABLE `xv_dashboard_drill` (
  `board_id` int(8) NOT NULL,
  `drill_id` int(8) NOT NULL,
  `drill_name` varchar(16) NOT NULL,
  `drill_title` varchar(16) NOT NULL DEFAULT 'New Tab'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for xv_dashboard_rel
-- 关系表 ， 用来关联一个databoard的datamodel和chart
-- ----------------------------
DROP TABLE IF EXISTS `xv_dashboard_rel`;
CREATE TABLE `xv_dashboard_rel` (
  `dashboard_id` int(4) NOT NULL,
  `data_model_id` int(4) NOT NULL,
  `chart_id` int(4) DEFAULT NULL,
  `filter_id` int(4) NOT NULL DEFAULT '1',
  KEY `dashbaord` (`dashboard_id`),
  KEY `datamodel` (`data_model_id`),
  KEY `chart` (`chart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_dashboard_rel
-- ----------------------------
INSERT INTO `xv_dashboard_rel` VALUES ('1', '1', null, '1');


-- ----------------------------
-- Table structure for xv_datamodel
-- ----------------------------
DROP TABLE IF EXISTS `xv_datamodel`;
CREATE TABLE `xv_datamodel` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `ord_id` int(4) DEFAULT NULL COMMENT 'OrderField',
  `dashboard_id` int(4) NOT NULL COMMENT 'ThemeID',
  `description` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `datatheme` (`dashboard_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_datamodel
-- ----------------------------

INSERT INTO `xv_datamodel` VALUES ('1', '102', '1', 'platform');


-- ----------------------------
-- Table structure for xv_filter
-- ----------------------------
DROP TABLE IF EXISTS `xv_filter`;
CREATE TABLE `xv_filter` (
  `id` int(8) NOT NULL,
  `name` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_filter
-- ----------------------------
INSERT INTO `xv_filter` VALUES ('1', 'platform');


-- ----------------------------
-- Table structure for xv_filter_condition
-- ----------------------------
DROP TABLE IF EXISTS `xv_filter_condition`;
CREATE TABLE `xv_filter_condition` (
  `filter_id` int(8) NOT NULL,
  `col_id` int(8) NOT NULL,
  `level` int(2) NOT NULL DEFAULT '1',
  `code` int(4) NOT NULL DEFAULT '1',
  `type_id` int(8) NOT NULL DEFAULT '1',
  `value_id` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_filter_condition
-- ----------------------------
INSERT INTO `xv_filter_condition` VALUES ('1', '101', '1', '1', '1', null);
INSERT INTO `xv_filter_condition` VALUES ('1', '102', '1', '2', '1', null);
INSERT INTO `xv_filter_condition` VALUES ('1', '103', '1', '3', '1', null);




-- ----------------------------
-- Table structure for xv_filter_type
-- ----------------------------
DROP TABLE IF EXISTS `xv_filter_type`;
CREATE TABLE `xv_filter_type` (
  `id` int(4) NOT NULL,
  `value` varchar(32) NOT NULL,
  `text` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_filter_type
-- ----------------------------
INSERT INTO `xv_filter_type` VALUES ('1', 'gte', '大于等于');
INSERT INTO `xv_filter_type` VALUES ('1', 'gt', '大于');
INSERT INTO `xv_filter_type` VALUES ('1', 'eq', '等于');
INSERT INTO `xv_filter_type` VALUES ('1', 'lte', '小于等于');
INSERT INTO `xv_filter_type` VALUES ('1', 'lt', '小于');
INSERT INTO `xv_filter_type` VALUES ('2', 'in', '包含');
INSERT INTO `xv_filter_type` VALUES ('3', 'span', '区间为');
INSERT INTO `xv_filter_type` VALUES ('2', 'eq', '等于');
INSERT INTO `xv_filter_type` VALUES ('4', 'eq', '等于');

-- ----------------------------
-- Table structure for xv_filter_value
-- ----------------------------
DROP TABLE IF EXISTS `xv_filter_value`;
CREATE TABLE `xv_filter_value` (
  `id` int(8) NOT NULL,
  `value` varchar(32) NOT NULL,
  `text` varchar(32) NOT NULL,
  `code` int(4) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_filter_value
-- ----------------------------


-- ----------------------------
-- Table structure for xv_menu
-- ----------------------------
DROP TABLE IF EXISTS `xv_menu`;
CREATE TABLE `xv_menu` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `url` varchar(256) DEFAULT NULL,
  `subject` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `code` int(11) NOT NULL COMMENT 'order code',
  `valid` int(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_menu
-- ----------------------------
INSERT INTO `xv_menu` VALUES ('1', '报表', null, '1', '1', '1', '1');
INSERT INTO `xv_menu` VALUES ('2', '平台报表', '/board/1', '1', '2', '1', '1');
INSERT INTO `xv_menu` VALUES ('3', '广告主报表', '/board/2', '1', '2', '2', '1');
INSERT INTO `xv_menu` VALUES ('4', '计划报表', '/board/3', '1', '2', '3', '1');
INSERT INTO `xv_menu` VALUES ('5', '活动报表', '/board/4', '1', '2', '4', '1');
INSERT INTO `xv_menu` VALUES ('6', '媒体主报表', '/board/5', '1', '2', '5', '1');
INSERT INTO `xv_menu` VALUES ('7', 'APP报表', '/board/6', '1', '2', '6', '1');




-- ----------------------------
-- Table structure for xv_metadata_column
-- ----------------------------
DROP TABLE IF EXISTS `xv_metadata_column`;
CREATE TABLE `xv_metadata_column` (
  `id` int(4) NOT NULL,
  `name` varchar(32) NOT NULL COMMENT '字段名',
  `label` varchar(32) NOT NULL COMMENT '页面显示的名字' ,
  `dim` int(2) NOT NULL COMMENT '1：维度， 0：指标',
  `datatype` varchar(16) DEFAULT NULL COMMENT ,
  `func` varchar(64) DEFAULT NULL,
  `paras` varchar(32) DEFAULT NULL COMMENT '参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_metadata_column
-- ----------------------------
INSERT INTO `xv_metadata_column` VALUES ('101', 'rep_date', '日期', '1', 'map', null, 'rep_date');
INSERT INTO `xv_metadata_column` VALUES ('102', 'platform_cost', '平台支出', '0', 'digit', 'sum(platform_cost)', '');
INSERT INTO `xv_metadata_column` VALUES ('103', 'platform_income', '平台收入', '0', 'digit', 'sum(platform_income)', '');
INSERT INTO `xv_metadata_column` VALUES ('104', 'platform_profit', '平台毛利', '0', 'digit', 'sum(platform_income-platform_income)', '');
INSERT INTO `xv_metadata_column` VALUES ('105', 'impr_num', '曝光量', '0', 'int', 'sum(impr_num)', '');
INSERT INTO `xv_metadata_column` VALUES ('106', 'watch_num', '观看量', '0', 'int', 'sum(watch_num)', '');
INSERT INTO `xv_metadata_column` VALUES ('107', 'watch_rate', '观看率', '0', 'digit', 'sum(watch_num)/sum(impr_num)*100', '');
INSERT INTO `xv_metadata_column` VALUES ('108', 'watch_done_num', '看完量', '0', 'int', 'sum(watch_done_num)', '');
INSERT INTO `xv_metadata_column` VALUES ('110', 'watch_done_rate', '看完率', '0', 'digit', 'sum(watch_done_num)/sum(watch_num)*100', '');
INSERT INTO `xv_metadata_column` VALUES ('111', 'click_num', '点击量', '0', 'int', 'sum(click_num)', '');
INSERT INTO `xv_metadata_column` VALUES ('112', 'click_rate', '点击率', '0', 'digit', 'sum(click_num)/sum(impr_num)*100', '');
INSERT INTO `xv_metadata_column` VALUES ('113', 'avg_cpm', '平均CPM', '0', 'digit', 'sum(avg_cpm)', '');
INSERT INTO `xv_metadata_column` VALUES ('114', 'avg_cpm', '平均CPC', '0', 'digit', 'sum(avg_cpc)', '');
INSERT INTO `xv_metadata_column` VALUES ('115', 'target_done_num', '目标完成量', '0', 'int', 'sum(target_done_num)', '');
INSERT INTO `xv_metadata_column` VALUES ('116', 'target_done_rate', '目标完成率', '0', 'digit', 'sum(target_done_num)/sum(target_num)*100', '');
INSERT INTO `xv_metadata_column` VALUES ('117', 'target_done_avg_price', '目标完成均价', '0', 'digit', 'sum(target_done_price)/sum(target_done_num)', '');


-- ----------------------------
-- Table structure for xv_user_page
-- ----------------------------
DROP TABLE IF EXISTS `xv_user_page`;
CREATE TABLE `xv_user_page` (
  `uid` int(8) NOT NULL,
  `title` varchar(32) DEFAULT NULL,
  `url` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xv_user_page
-- ----------------------------
INSERT INTO `xv_user_page` VALUES ('100865', 'Welcome', '/frame/hello.jsp');
INSERT INTO `xv_user_page` VALUES ('100916', '我的模板', '/template/9');
