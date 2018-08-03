/*
 Navicat Premium Data Transfer

 Source Server         : mysql学习测试
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 192.168.2.178:3306
 Source Schema         : shiro

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 03/08/2018 09:21:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_properties
-- ----------------------------
DROP TABLE IF EXISTS `t_properties`;
CREATE TABLE `t_properties`  (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `key_` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `value_` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `createDate` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key-unique`(`key_`) USING BTREE COMMENT 'key 不能重复'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
