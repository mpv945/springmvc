package org.haijun.study.model.dto;

public class PermissionDO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源类型：menu,button,
     */
    private String type;

    /**
     * 访问url地址
     */
    private String url;

    /**
     * 权限代码字符串
     */
    private String percode;

    /**
     * 父结点id
     */
    private Long parentid;

    /**
     * 父结点id列表串
     */
    private String parentids;

    /**
     * 排序号
     */
    private String sortstring;

    /**
     * 是否可用,1：可用，0不可用
     */
    private Boolean available;

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
     * 获取资源名称
     *
     * @return name - 资源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     *
     * @param name 资源名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取资源类型：menu,button,
     *
     * @return type - 资源类型：menu,button,
     */
    public String getType() {
        return type;
    }

    /**
     * 设置资源类型：menu,button,
     *
     * @param type 资源类型：menu,button,
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取访问url地址
     *
     * @return url - 访问url地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置访问url地址
     *
     * @param url 访问url地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取权限代码字符串
     *
     * @return percode - 权限代码字符串
     */
    public String getPercode() {
        return percode;
    }

    /**
     * 设置权限代码字符串
     *
     * @param percode 权限代码字符串
     */
    public void setPercode(String percode) {
        this.percode = percode;
    }

    /**
     * 获取父结点id
     *
     * @return parentid - 父结点id
     */
    public Long getParentid() {
        return parentid;
    }

    /**
     * 设置父结点id
     *
     * @param parentid 父结点id
     */
    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    /**
     * 获取父结点id列表串
     *
     * @return parentids - 父结点id列表串
     */
    public String getParentids() {
        return parentids;
    }

    /**
     * 设置父结点id列表串
     *
     * @param parentids 父结点id列表串
     */
    public void setParentids(String parentids) {
        this.parentids = parentids;
    }

    /**
     * 获取排序号
     *
     * @return sortstring - 排序号
     */
    public String getSortstring() {
        return sortstring;
    }

    /**
     * 设置排序号
     *
     * @param sortstring 排序号
     */
    public void setSortstring(String sortstring) {
        this.sortstring = sortstring;
    }

    /**
     * 获取是否可用,1：可用，0不可用
     *
     * @return available - 是否可用,1：可用，0不可用
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * 设置是否可用,1：可用，0不可用
     *
     * @param available 是否可用,1：可用，0不可用
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }
}