/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : test2

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2017-05-26 11:27:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for data_category_url
-- ----------------------------
DROP TABLE IF EXISTS `data_category_url`;
CREATE TABLE `data_category_url` (
  `data_category` varchar(32) NOT NULL,
  `service_type` varchar(32) NOT NULL,
  `call_interface_type` varchar(32) DEFAULT NULL COMMENT '1--hessian\r\n',
  `call_url` varchar(128) DEFAULT NULL,
  `call_back_url` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`data_category`,`service_type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='按照data_category_url增删修改查询';

-- ----------------------------
-- Records of data_category_url
-- ----------------------------


-- ----------------------------
-- Table structure for tb_common_biz
-- ----------------------------
DROP TABLE IF EXISTS `tb_common_biz`;
CREATE TABLE `tb_common_biz` (
  `data_id` bigint(20) NOT NULL,
  `data_category` varchar(32) DEFAULT NULL,
  `service_type` varchar(32) DEFAULT NULL,
  `group_id` varchar(32) DEFAULT NULL,
  `group_name` varchar(64) DEFAULT NULL,
  `create_person_id` varchar(16) DEFAULT NULL,
  `create_person_name` varchar(64) DEFAULT NULL,
  `create_person_telno` varchar(32) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_person` varchar(16) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `result` varchar(256) DEFAULT NULL,
  `result_ext` varchar(256) DEFAULT NULL,
  `service_owner` varchar(64) DEFAULT NULL,
  `process_instance_id` varchar(64) DEFAULT NULL,
  `task_id` varchar(64) DEFAULT NULL,
  `ext_activiti_info` varchar(256) DEFAULT NULL,
  `status` varchar(256) DEFAULT NULL,
  `ext_status` varchar(256) DEFAULT NULL,
  `data1` longtext,
  `data2` longtext,
  `data3` longtext,
  `data4` longtext,
  `data5` longtext,
  `data6` longtext,
  `data7` longtext,
  `data8` longtext,
  `data9` longtext,
  `data10` longtext,
  PRIMARY KEY (`data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (data_id)
(PARTITION p201704 VALUES LESS THAN (2017050100010000001) ENGINE = InnoDB,
 PARTITION p201705 VALUES LESS THAN (2017060100010000001) ENGINE = InnoDB,
 PARTITION p201706 VALUES LESS THAN (2017070100010000001) ENGINE = InnoDB,
 PARTITION p201707 VALUES LESS THAN (2017080100010000001) ENGINE = InnoDB,
 PARTITION p201708 VALUES LESS THAN (2017090100010000001) ENGINE = InnoDB,
 PARTITION p201709 VALUES LESS THAN (2017100100010000001) ENGINE = InnoDB,
 PARTITION p201710 VALUES LESS THAN (2017110100010000001) ENGINE = InnoDB,
 PARTITION p201711 VALUES LESS THAN (2017120100010000001) ENGINE = InnoDB,
 PARTITION p201712 VALUES LESS THAN (2018010100010000001) ENGINE = InnoDB,
 PARTITION p201801 VALUES LESS THAN (2018020100010000001) ENGINE = InnoDB,
 PARTITION p201802 VALUES LESS THAN (2018030100010000001) ENGINE = InnoDB,
 PARTITION p201803 VALUES LESS THAN (2018040100010000001) ENGINE = InnoDB,
 PARTITION p201804 VALUES LESS THAN (2018050100010000001) ENGINE = InnoDB,
 PARTITION p201805 VALUES LESS THAN (2018060100010000001) ENGINE = InnoDB,
 PARTITION p201806 VALUES LESS THAN (2018070100010000001) ENGINE = InnoDB,
 PARTITION p201807 VALUES LESS THAN (2018080100010000001) ENGINE = InnoDB,
 PARTITION p201808 VALUES LESS THAN (2018090100010000001) ENGINE = InnoDB,
 PARTITION p201809 VALUES LESS THAN (2018100100010000001) ENGINE = InnoDB,
 PARTITION p201810 VALUES LESS THAN (2018110100010000001) ENGINE = InnoDB,
 PARTITION p201811 VALUES LESS THAN (2018120100010000001) ENGINE = InnoDB,
 PARTITION p201812 VALUES LESS THAN (2019010100010000001) ENGINE = InnoDB,
 PARTITION p2019 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Records of tb_common_biz
-- ----------------------------

-- ----------------------------
-- Table structure for tb_data_createtime
-- ----------------------------
DROP TABLE IF EXISTS `tb_data_createtime`;
CREATE TABLE `tb_data_createtime` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `data_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='按照create_time查询，能够输入一段时间';

-- ----------------------------
-- Records of tb_data_createtime
-- ----------------------------

-- ----------------------------
-- Table structure for tb_data_privilege
-- ----------------------------
DROP TABLE IF EXISTS `tb_data_privilege`;
CREATE TABLE `tb_data_privilege` (
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `privilege_person` int(11) NOT NULL,
  `person_or_role` int(11) DEFAULT NULL,
  `is_creator` int(11) DEFAULT NULL,
  `data_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`create_time`,`privilege_person`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE ( month(create_time))
SUBPARTITION BY HASH (privilege_person)
SUBPARTITIONS 1
(PARTITION p0 VALUES LESS THAN (1) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (2) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (3) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (4) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (5) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN (6) ENGINE = InnoDB,
 PARTITION p6 VALUES LESS THAN (7) ENGINE = InnoDB,
 PARTITION p7 VALUES LESS THAN (8) ENGINE = InnoDB,
 PARTITION p8 VALUES LESS THAN (9) ENGINE = InnoDB,
 PARTITION p9 VALUES LESS THAN (10) ENGINE = InnoDB,
 PARTITION p10 VALUES LESS THAN (11) ENGINE = InnoDB,
 PARTITION p11 VALUES LESS THAN (12) ENGINE = InnoDB) */;


-- ----------------------------
-- Table structure for xw_sequence
-- ----------------------------
DROP TABLE IF EXISTS `xw_sequence`;
CREATE TABLE `xw_sequence` (
  `SEQ_NAME` varchar(64) NOT NULL,
  `INIT_VALUE` decimal(16,0) NOT NULL,
  `MAX_VALUE` decimal(16,0) NOT NULL,
  `CURRENT_VALUE` decimal(16,0) NOT NULL,
  `SEQ_STEP` decimal(8,0) NOT NULL,
  `TABLE_CIRCLE_SIZE` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xw_sequence
-- ----------------------------
INSERT INTO `xw_sequence` VALUES ('common_biz_seq', '1', '9999999999', '1', '1', '10');
INSERT INTO `xw_sequence` VALUES ('group_id_seq', '1', '9999', '1', '1', '10');
INSERT INTO `xw_sequence` VALUES ('data_id_seq', '1', '9999999999999999', '1', '1', '10');

-- ----------------------------
-- Procedure structure for pro_getSequenceByName
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_getSequenceByName`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_getSequenceByName`( IN sn varchar(64),IN amt numeric(20,0),OUT seqName varchar(64),OUT initValue numeric(20,0),OUT max_Value numeric

(20,0),OUT currentValue numeric(20,0),OUT seqStep numeric(20,0), OUT tableCircleSize numeric(20,0))
BEGIN
	#Routine body goes here...
 DECLARE t_error INTEGER DEFAULT 0;  
 DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1; 

  SELECT * INTO seqName,initValue,max_Value,currentValue,seqStep, tableCircleSize FROM xw_sequence WHERE seq_name = sn FOR Update;
  UPDATE xw_sequence SET current_value = current_value + seqStep*amt WHERE seq_name = sn;

 IF t_error = 1 THEN  
     ROLLBACK;  
 ELSE  
 COMMIT;  
 END IF;  
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for insert_event_example
-- ----------------------------
DROP EVENT IF EXISTS `insert_event_example`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `insert_event_example` ON SCHEDULE EVERY 5 SECOND STARTS '2017-05-10 03:36:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
    INSERT INTO test_event(`name`,`create_time`,`update_time`) VALUES(UUID(),NOW(),NOW());
END
;;
DELIMITER ;
