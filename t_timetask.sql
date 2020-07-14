/*
 Navicat MySQL Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 14/07/2020 21:09:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_timetask
-- ----------------------------
DROP TABLE IF EXISTS `t_timetask`;
CREATE TABLE `t_timetask`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `group_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` timestamp(0) NULL DEFAULT NULL,
  `end_time` timestamp(0) NULL DEFAULT NULL,
  `cron` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_status` tinyint(255) NULL DEFAULT NULL,
  `is_concurrent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_data` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bean_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `discription` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creat_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creat_time` timestamp(0) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  `in_use` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_timetask
-- ----------------------------
INSERT INTO `t_timetask` VALUES ('0abba7d4a51a43da92aaa97e887a9ff1', 'as', 'as', '2020-07-14 00:00:00', '2020-07-14 00:00:00', '0 0/1 * * * ? ', 0, '1', 'fsw', 'com.xc.timetask.controller.TaskDemo', 'run', 'ss', 'aaa', '2020-07-07 12:14:08', '2020-07-13 12:34:13', '0');
INSERT INTO `t_timetask` VALUES ('1', 'afw', 'wf', '2020-07-07 00:00:00', '2020-07-07 00:00:00', '0 0/1 * * * ? ', 0, '0', 'fw', 'com.xc.timetask.controller.TaskDemo', 'run', 'afw', 'admin', '2020-06-17 10:12:07', '2020-06-17 10:12:07', '0');

SET FOREIGN_KEY_CHECKS = 1;
