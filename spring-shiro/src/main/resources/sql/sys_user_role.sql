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

 Date: 01/08/2018 17:42:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL,
  `sys_user_id` bigint(20) NOT NULL,
  `sys_role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和权限关联表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
