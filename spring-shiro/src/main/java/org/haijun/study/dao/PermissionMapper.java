package org.haijun.study.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.haijun.study.entity.Permission;
import org.haijun.study.model.dto.PermissionDO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PermissionMapper extends Mapper<Permission> {

    /**
     * 查询角色对应的菜单列表
     * where标记的作用类似于动态sql中的set标记，他的作用主要是用来简化sql语句中where条件判断的书写的
     *      ${}与#{}的区别：${}解析穿过来的参数值不带单引号（基本的数字类型），#{}解析传过来参数带单引号（可以防止SQL注入）。
     *          同时 对于LIKE 也是不能够直接使用的 可以借助concat函数实现email LIKE concat(#{prefix},'%')
     *          where 标记会自动将其后第一个条件的and或者是or给忽略掉; 基本使用语法
     *          <if test="fwbdh != null and fwbdh != ''">
     *          <when test='fwbdh.indexOf(",") != -1'> 调用java的java.lang.String中定义的方法
     * @param roles
     * @return
     */
    @Select("<script> " +
            "SELECT * FROM sys_permission WHERE id IN ( " +
            "SELECT sys_permission_id FROM sys_role_permission " +
            "<where>" +
            "<choose>" +
            "<when test='roles != null and roles.size()>0' >" +
            "sys_role_id IN " +
            "<foreach collection='roles' open='(' close=')' separator=',' item='item' index='index'> " +
            "#{item}" + //#{orgId,jdbcType=BIGINT}
            "</foreach> " +
            "</when> " +
            "<otherwise>" +
            "sys_role_id IS NULL " +
            "</otherwise> " +
            "</choose> " +
            "</where> " +
            ") " +
            "<if test=\"type != null and type != ''\">" +
            "and type=#{type} " +
            "</if> "+
            "</script>")
    List<PermissionDO> findMenuListByRoles(@Param("roles") List<Long> roles,@Param("type") String type);// 如果参数时map类型，不要@Param，直接取值就用key
}