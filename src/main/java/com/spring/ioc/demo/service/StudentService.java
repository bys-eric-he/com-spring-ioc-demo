package com.spring.ioc.demo.service;

/**
 * 定义接口,通过反射方式代理具体实现,动态代理只能代理接口.
 */
public interface StudentService {
    void buy();
    String talk();
}
