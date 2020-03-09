package com.spring.ioc.demo.service.impl;

import com.spring.ioc.demo.service.CalculateService;
import org.springframework.stereotype.Service;

@Service("calculateService")
public class CalculateServiceImpl implements CalculateService {

    private String desc = "CalculateServiceImpl -> desc from calculate service";

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public String getServiceDesc() {
        return desc;
    }

    @Override
    public void setServiceDesc(String desc) {
        this.desc = desc;
    }
}
