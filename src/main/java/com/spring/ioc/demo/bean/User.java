package com.spring.ioc.demo.bean;

import java.io.Serializable;

/**
 * 定义一个用户对象bean
 */
public class User implements Serializable {
    private static final long serialVersionUID = 2326150446923112363L;
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

    /**
     * 覆盖equals方法
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        if (!age.equals(user.age)) {
            return false;
        }
        if (userName == null) {
            return user.userName == null;
        } else {
            return userName.equals(user.userName);
        }
    }
}
