package org.haijun.study.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Table(name = "`sys_role_permission`")
public class RolePermission {
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Long id;

    /**
     * 角色id
     */
    @Column(name = "`sys_role_id`")
    private Integer sysRoleId;

    /**
     * 权限id
     */
    @Column(name = "`sys_permission_id`")
    private Long sysPermissionId;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取角色id
     *
     * @return sys_role_id - 角色id
     */
    public Integer getSysRoleId() {
        return sysRoleId;
    }

    /**
     * 设置角色id
     *
     * @param sysRoleId 角色id
     */
    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    /**
     * 获取权限id
     *
     * @return sys_permission_id - 权限id
     */
    public Long getSysPermissionId() {
        return sysPermissionId;
    }

    /**
     * 设置权限id
     *
     * @param sysPermissionId 权限id
     */
    public void setSysPermissionId(Long sysPermissionId) {
        this.sysPermissionId = sysPermissionId;
    }
}