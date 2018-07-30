package org.haijun.study.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Table(name = "`sys_role`")
public class Role {
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private String id;

    @Column(name = "`name`")
    private String name;

    /**
     * 是否可用,1：可用，0不可用
     */
    @Column(name = "`available`")
    private String available;

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
    public String getAvailable() {
        return available;
    }

    /**
     * 设置是否可用,1：可用，0不可用
     *
     * @param available 是否可用,1：可用，0不可用
     */
    public void setAvailable(String available) {
        this.available = available;
    }
}