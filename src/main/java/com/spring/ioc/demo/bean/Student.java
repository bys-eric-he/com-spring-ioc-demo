package com.spring.ioc.demo.bean;

import java.io.Serializable;

/**
 * 定义一个Student对象Bean
 */
public class Student implements Serializable {

    private static final long serialVersionUID = -1959718675684859184L;
    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}