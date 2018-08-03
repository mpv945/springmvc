package org.haijun.study.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Table(name = "`sys_user_role`")
public class UserRole {
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Long id;

    @Column(name = "`sys_user_id`")
    private Long sysUserId;

    @Column(name = "`sys_role_id`")
    private Integer sysRoleId;

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
     * @return sys_user_id
     */
    public Long getSysUserId() {
        return sysUserId;
    }

    /**
     * @param sysUserId
     */
    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * @return sys_role_id
     */
    public Integer getSysRoleId() {
        return sysRoleId;
    }

    /**
     * @param sysRoleId
     */
    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }
}