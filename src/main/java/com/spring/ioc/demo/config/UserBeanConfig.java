package com.spring.ioc.demo.config;

import com.spring.ioc.demo.bean.User;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * 定义一个注解配置文件 必须要加上@Configuration注解
 */
@Configurable
public class UserBeanConfig {
    /**
     * 定义一个bean对象
     *
     * @return
     */
    @Bean(value = "user-bean", name = "user-bean", initMethod = "initUser", destroyMethod = "destroyUser")
    public User getUser() {
        return new User("sky.zhang", 28);
    }
}
