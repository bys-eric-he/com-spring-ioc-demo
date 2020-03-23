package com.spring.ioc.demo.service.impl;

import com.spring.ioc.demo.service.StudentService;

/**
 * 学生行为实现
 */
public class StudentServiceImpl implements StudentService {
    @Override
    public void buy() {
        System.out.println("买");

    }

    @Override
    public String talk() {
        System.out.println("说");
        return "说";
    }

}
