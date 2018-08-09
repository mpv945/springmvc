package org.haijun.study.lambda;

public class Person {

    public enum Sex {
        MALE, FEMALE
    }
    private String name;

    private Integer age ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Sex getGender() {
        return gender;
    }

    public void setGender(Sex gender) {
        this.gender = gender;
    }

    private Sex gender;

    public Person(String name, Integer age, Sex gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
