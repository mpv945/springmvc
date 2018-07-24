CREATE TABLE `t_test` (
  `id` bigint(18) NOT NULL COMMENT '主键',
  `name` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `age` int(3) NOT NULL COMMENT '年龄'
) ENGINE=InnoDB DEFAULT CHARSET=utf8_general_ci;

ALTER TABLE `t_test`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `t_test`
  MODIFY `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键';
COMMIT;