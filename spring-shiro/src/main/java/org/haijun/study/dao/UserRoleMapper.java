package org.haijun.study.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.haijun.study.entity.UserRole;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserRoleMapper extends Mapper<UserRole> {

    @Select("select sys_role_id from sys_user_role where sys_user_id=${userId}")
    List<Long> findRolesByUserId(@Param("userId") long userId);
}