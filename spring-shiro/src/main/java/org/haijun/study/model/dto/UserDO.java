package org.haijun.study.model.dto;

public class UserDO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 账号
     */
    private String usercode;

    /**
     * 姓名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 账号是否锁定，1：锁定，0未锁定
     */
    private String locked;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取账号
     *
     * @return usercode - 账号
     */
    public String getUsercode() {
        return usercode;
    }

    /**
     * 设置账号
     *
     * @param usercode 账号
     */
    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    /**
     * 获取姓名
     *
     * @return username - 姓名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置姓名
     *
     * @param username 姓名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取盐
     *
     * @return salt - 盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置盐
     *
     * @param salt 盐
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取账号是否锁定，1：锁定，0未锁定
     *
     * @return locked - 账号是否锁定，1：锁定，0未锁定
     */
    public String getLocked() {
        return locked;
    }

    /**
     * 设置账号是否锁定，1：锁定，0未锁定
     *
     * @param locked 账号是否锁定，1：锁定，0未锁定
     */
    public void setLocked(String locked) {
        this.locked = locked;
    }
}