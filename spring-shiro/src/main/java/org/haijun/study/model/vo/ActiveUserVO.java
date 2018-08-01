package org.haijun.study.model.vo;

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
}
