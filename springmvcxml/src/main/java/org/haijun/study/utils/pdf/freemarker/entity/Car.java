package org.haijun.study.utils.pdf.freemarker.entity;

import java.io.Serializable;

public class Car implements Serializable {

    private static final long serialVersionUID = 2525396843784586950L;
    private String name;

    private Float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
