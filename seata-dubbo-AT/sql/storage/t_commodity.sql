/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3307_mysql5.7
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3307
 Source Schema         : seata_storage

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 05/05/2020 20:10:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_commodity
-- ----------------------------
DROP TABLE IF EXISTS `t_commodity`;
CREATE TABLE `t_commodity`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `commodity_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `commodity_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `stock` int(11) NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_commodity
-- ----------------------------
INSERT INTO `t_commodity` VALUES (1, 'a0001', '外星人笔记本', 10, 1588680643737, 1588680643737);

SET FOREIGN_KEY_CHECKS = 1;
