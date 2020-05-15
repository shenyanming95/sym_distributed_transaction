-- 创建数据库
CREATE DATABASE icbc_bank DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE icbc_bank;

-- 创建实体表
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cellphone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `total_balance` decimal(8, 0) NULL DEFAULT NULL,
  `frozen_balance` decimal(8, 0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 创建日志表
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `serial_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `cellphone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `current_total_balance` decimal(8, 0) NULL DEFAULT NULL,
  `current_frozen_balance` decimal(8, 0) NULL DEFAULT NULL,
  `transfer_balance` decimal(8, 0) NULL DEFAULT NULL,
  `operation_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 插入账户信息
INSERT INTO `icbc_bank`.`t_account`(`cellphone`, `username`, `total_balance`, `frozen_balance`, `update_time`) VALUES ('10000', 'lisi', 1000, 0, now());