package org.haijun.study.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Table(name = "`sys_role_permission`")
public class RolePermission {
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private String id;

    /**
     * 角色id
     */
    @Column(name = "`sys_role_id`")
    private String sysRoleId;

    /**
     * 权限id
     */
    @Column(name = "`sys_permission_id`")
    private String sysPermissionId;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取角色id
     *
     * @return sys_role_id - 角色id
     */
    public String getSysRoleId() {
        return sysRoleId;
    }

    /**
     * 设置角色id
     *
     * @param sysRoleId 角色id
     */
    public void setSysRoleId(String sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    /**
     * 获取权限id
     *
     * @return sys_permission_id - 权限id
     */
    public String getSysPermissionId() {
        return sysPermissionId;
    }

    /**
     * 设置权限id
     *
     * @param sysPermissionId 权限id
     */
    public void setSysPermissionId(String sysPermissionId) {
        this.sysPermissionId = sysPermissionId;
    }
}