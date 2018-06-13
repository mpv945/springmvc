DROP TABLE IF EXISTS `t_property_config`;
CREATE TABLE `t_property_config`  (
  `property_key` varchar(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'key值',
  `property_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'vlaue值',
  `deleted` int(1) NULL DEFAULT NULL COMMENT '0:表示删除状态为false，1:表示删除状态为true',
  PRIMARY KEY (`property_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;
DELETE FROM `t_property_config`;