package com.spring.ioc.demo.bean;

/**
 * 定义一个用户对象bean
 */
public class User {
    private String userName;
    private Integer age;

    public User() {
    }

    public User(String userName, Integer age) {
        this.userName = userName;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void initUser() {
        System.out.println("初始化用户bean之前执行!");
    }

    public void destroyUser() {
        System.out.println("bean销毁之后执行!");
    }
}