package org.haijun.study.model;

import javax.persistence.*;

@Table(name = "`t_test`")
public class TkTest {
    /**
     * 涓婚敭
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 姓名
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 邮箱
     */
    @Column(name = "`email`")
    private String email;

    /**
     * 年龄
     */
    @Column(name = "`age`")
    private Integer age;

    /**
     * 获取涓婚敭
     *
     * @return id - 涓婚敭
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置涓婚敭
     *
     * @param id 涓婚敭
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }
}