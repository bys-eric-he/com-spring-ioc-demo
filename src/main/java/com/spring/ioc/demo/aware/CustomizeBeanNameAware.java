package com.spring.ioc.demo.aware;

import com.spring.ioc.demo.util.SpringTrackUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;

/**
 * 通过Aware接口得到想要的对象
 * Spring提供了很多Aware接口, 比如BeanFactoryAware、 ApplicationContextAware、ResourceLoaderAware、 ServletContextAware等等.
 * 这些接口一般都有个setXXX来设置对应的组件. 如果我们的Bean实现了这些Aware的时候就可以获取对应的资源.
 */
@Service
public class CustomizeBeanNameAware implements BeanNameAware {
    private String beanName;

    @Override
    public void setBeanName(String beanName) {
        SpringTrackUtils.printTrack("beanName is set to " + beanName);
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }
}
