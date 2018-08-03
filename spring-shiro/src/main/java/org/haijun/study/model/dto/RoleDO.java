package org.haijun.study.model.dto;

public class RoleDO {

    private Integer id;

    /**
     * 角色名字
     */
    private String name;

    /**
     * 是否可用,1：可用，0不可用
     */
    private Boolean available;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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