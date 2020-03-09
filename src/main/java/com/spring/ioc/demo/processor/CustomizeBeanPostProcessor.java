package com.spring.ioc.demo.processor;

import com.spring.ioc.demo.service.CalculateService;
import com.spring.ioc.demo.util.SpringTrackUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 控制Bean, 这个接口是Bean的后处理器, 通过它我们可以在Bean初始化前后做一些操作. 它提供了两个方法:
 * AbstractApplicationContext类的refresh方法 registerBeanPostProcessors(beanFactory);
 * <p>
 * postProcessBeforeInitialization: 在Bean初始化之 前 做一些操作
 * postProcessAfterInitialization: 在Bean初始化之 后 做一些操作
 * 注意这两个方法都有两个参数: bean和beanName, 并需要返回一个对象.
 * 所有的Bean在被初始化前后都会调用这两个方法, 我们可以过滤出所关心的Bean对其进行一些操作,比如把某个Bean替换成代理对象等.
 * 创建BeanPostProcessor的实现类CustomizeBeanPostProcessor，这里面会修改calculateService实例的成员变量serviceDesc的值
 */
@Component
public class CustomizeBeanPostProcessor implements BeanPostProcessor {
    /**
     * 在Bean初始化之 前 做一些操作
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("calculateService".equals(beanName)) {
            //打印当前堆栈
            SpringTrackUtils.printTrack("->calculateService bean do postProcess before initialization");
            CalculateService calculateService = (CalculateService) bean;
            //修改calculateService实例的成员变量serviceDesc的值
            calculateService.setServiceDesc("desc from " + this.getClass().getSimpleName());
        }
        return bean;
    }

    /**
     * 在Bean初始化之 后 做一些操作
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("calculateService".equals(beanName)) {
            //打印当前堆栈
            SpringTrackUtils.printTrack("->calculateService bean do postProcess after initialization");
        }
        return bean;
    }
}