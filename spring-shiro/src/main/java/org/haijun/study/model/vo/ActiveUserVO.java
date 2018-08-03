package org.haijun.study.model.vo;

import org.haijun.study.model.dto.PermissionDO;
import org.haijun.study.model.dto.RoleDO;

import java.util.List;

public class ActiveUserVO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userCode;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 权限列表
     */
    private List<PermissionDO> permissionDOS;

    /**
     * 角色列表
     */
    private List<RoleDO> roleDOS;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<PermissionDO> getPermissionDOS() {
        return permissionDOS;
    }

    public void setPermissionDOS(List<PermissionDO> permissionDOS) {
        this.permissionDOS = permissionDOS;
    }

    public List<RoleDO> getRoleDOS() {
        return roleDOS;
    }

    public void setRoleDOS(List<RoleDO> roleDOS) {
        this.roleDOS = roleDOS;
    }
}
