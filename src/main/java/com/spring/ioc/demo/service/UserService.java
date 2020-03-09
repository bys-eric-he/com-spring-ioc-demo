package com.spring.ioc.demo.service;

/**
 * 定义接口,无具体实现,通过反射方式代理具体实现,动态代理只能代理接口.
 */
public interface UserService {
    Integer getUserCount();

    String getUserInfo(Integer id);

    Integer addUser(String name, String email, Integer age, Integer sex, String schoolName);
}