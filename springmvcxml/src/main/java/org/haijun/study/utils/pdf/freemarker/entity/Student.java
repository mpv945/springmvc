package org.haijun.study.utils.pdf.freemarker.entity;

import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = 6465853142793754140L;
    private Integer id;
    private String name;

    private Car car;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
