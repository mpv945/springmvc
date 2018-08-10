package org.haijun.study.model.vo;

import org.haijun.study.model.dto.PermissionDO;
import org.haijun.study.model.dto.RoleDO;

import java.io.Serializable;
import java.util.List;

/**
 * 该类需要shiro保存到redis，所以需要序列化
 *
 * File -> Settings… -> Editor -> Inspections -> Serialization issues[在java类目下] ->
 * Serializable class without ‘serialVersionUID’（选中）
 * 进入实现了Serializable中的类，选中类名，Alt+Enter弹出提示，然后直接导入生成 SerializableID
 */
public class ActiveUserVO implements Serializable {

    private static final long serialVersionUID = -6444825324187074485L;
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
