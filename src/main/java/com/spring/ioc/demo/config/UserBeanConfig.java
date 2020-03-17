package com.spring.ioc.demo.config;

import com.spring.ioc.demo.bean.User;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * 定义一个注解配置文件 必须要加上@Configuration注解
 */
@Configurable
public class UserBeanConfig {
    /**
     * 定义一个bean对象
     * #@Lazy 默认值为true,加上注解后User Bean在容器初始化的时候不会去实例化,而在第一次调用时getBean(User.class)时，才会实例化。
     *
     * @return
     */
    @Lazy
    @Bean(value = "user-bean", name = "user-bean", initMethod = "initUser", destroyMethod = "destroyUser")
    public User getUser() {

        System.out.println("创建User Bean实例.....");
        return new User("sky.zhang", 28);
    }
}
