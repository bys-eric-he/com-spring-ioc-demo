package com.spring.ioc.demo.config;

import com.spring.ioc.demo.factory.PojoFactoryBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

@Configurable
public class PojoFactoryBeanConfig {
    /**
     * 定义一个bean对象
     * #@Lazy 默认值为true,加上注解后User Bean在容器初始化的时候不会去实例化,而在第一次调用时getBean(User.class)时，才会实例化。
     *
     * @return
     */
    @Lazy
    @Bean(value = "pojo-factory-bean", name = "pojo-factory-bean")
    public PojoFactoryBean getPojoFactoryBean() {

        System.out.println("创建POJO Factory Bean实例.....");
        PojoFactoryBean pojoFactoryBean = new PojoFactoryBean();
        pojoFactoryBean.setType("School");
        return pojoFactoryBean;
    }
}
