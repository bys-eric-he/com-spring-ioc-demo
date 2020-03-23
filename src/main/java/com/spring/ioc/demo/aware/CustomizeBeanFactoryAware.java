package com.spring.ioc.demo.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * 在使用spring编程时，常常会遇到想根据bean的名称来获取相应的bean对象，这时候，就可以通过实现BeanFactoryAware来满足需求
 * 实现 BeanFactoryAware 接口的 bean 可以直接访问 Spring 容器，被容器创建以后，它会拥有一个指向 Spring 容器的引用，
 * 可以利用该bean根据传入参数动态获取被spring工厂加载的bean
 */
@Component
public class CustomizeBeanFactoryAware implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public static Object getBean(String beanName) {
        if (beanFactory == null) {
            throw new NullPointerException("BeanFactory is null!");
        }
        return beanFactory.getBean(beanName);
    }
}