package com.spring.ioc.demo.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * 从AbstractApplicationContext类的refresh方法看起，这里面对应着容器初始化的基本操作；
 * invokeBeanFactoryPostProcessors方法用来找出所有beanFactory后置处理器，并且调用这些处理器来改变bean的定义
 */
@Component
public class CustomizeBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    /**
     * BeanFactoryPostProcessor：是beanFactory的后置处理器；
     * <p>
     * 调用时机：在BeanFactory标准初始化之后调用，这时所有的bean定义已经保存加载到beanFactory，但是bean的实例还未创建.
     * <p>
     * 能干什么：来定制和修改BeanFactory的内容，如覆盖或添加属性.
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        AbstractBeanDefinition abstractBeanDefinition = (AbstractBeanDefinition) beanFactory.getBeanDefinition("calculateService");

        MutablePropertyValues pv = abstractBeanDefinition.getPropertyValues();
        //修改calculateService对象的desc属性值
        pv.addPropertyValue("desc", "Desc is changed from bean factory post processor");
        abstractBeanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);

        System.out.println("##修改calculateService对象的desc属性值->Desc is changed from bean factory post processor");
    }
}
