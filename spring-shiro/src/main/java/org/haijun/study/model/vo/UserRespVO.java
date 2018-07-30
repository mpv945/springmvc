package org.haijun.study.model.vo;

/**
 * 表现层对象(View Object)，主要对应展示界面显示的数据对象，用一个VO对象来封装整个界面展示所需要的对象数据。
 */
public class UserRespVO {

    private String id;

    /**
     * 账号
     */
    private String usercode;

    /**
     * 姓名
     */
    private String username;


    /**
     * 账号是否锁定，1：锁定，0未锁定
     */
    private String locked;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
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