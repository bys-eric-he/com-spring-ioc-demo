package com.spring.ioc.demo.controller;

import com.spring.ioc.demo.aware.CustomizeBeanNameAware;
import com.spring.ioc.demo.aware.SpringApplicationContextAware;
import com.spring.ioc.demo.service.CalculateService;
import com.spring.ioc.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class CustomizeController {
    @Autowired
    private CustomizeBeanNameAware customizeBeanNameAware;

    @Autowired(required = false)
    private CalculateService calculateService;

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserCount")
    public String getUserCount() {
        Integer userCount = userService.getUserCount();
        return userCount.toString();
    }

    @RequestMapping("/getUserInfo")
    public String getUserInfo() {
        return userService.getUserInfo(1);
    }

    @RequestMapping("/addUser")
    public String addUser() {
        Integer userId = userService.addUser("name", "abc@gmail.com", 16, 0, "Garden School");
        return userId.toString();
    }

    @GetMapping("/add/{a}/{b}")
    public String add(@PathVariable("a") int a, @PathVariable("b") int b) {
        return "add result : " + calculateService.add(a, b) + ", from [" + calculateService.getServiceDesc() + "]";
    }

    @RequestMapping("/getBeanDefinitionNames")
    public String getBeanDefinitionNames() {

        String[] beanDefinitionNames = SpringApplicationContextAware.getApplicationContext().getBeanDefinitionNames();

        StringBuilder stringBuilder = new StringBuilder();

        int arrayLength = 0;

        if (null != beanDefinitionNames) {
            arrayLength = beanDefinitionNames.length;
            //将所有bean的名称拼接成字符串（带html的换行符号<br>）
            for (String name : beanDefinitionNames) {
                stringBuilder.append(name).append("<br>");
            }
        }

        return "hello, "
                + new Date()
                + "<br><br>CustomizeBeanNameAware instance bean name : "
                + customizeBeanNameAware.getBeanName()
                + "<br><br>bean definition names, size "
                + arrayLength
                + ", detail :<br><br>"
                + stringBuilder;
    }
}
