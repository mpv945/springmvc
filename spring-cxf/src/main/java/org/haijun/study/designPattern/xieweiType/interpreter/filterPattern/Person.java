package org.haijun.study.designPattern.xieweiType.interpreter.filterPattern;

/**
 * 数据模型
 */
public class Person {
    private String name;
    private String gender;// 性别
    private String maritalStatus;//婚姻状态

    public Person(String name,String gender,String maritalStatus){
        this.name = name;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
    }

    public String getName() {
        return name;
    }
    public String getGender() {
        return gender;
    }
    public String getMaritalStatus() {
        return maritalStatus;
    }
}
