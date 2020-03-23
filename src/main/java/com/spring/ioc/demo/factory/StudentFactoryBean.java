package com.spring.ioc.demo.factory;

import com.spring.ioc.demo.bean.Student;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Service;

/**
 * AbstractFactoryBean
 * Spring提供了一个简单的实现了FactoryBean接口的抽象类，可以基于该抽象类定义我们自己的FactoryBean
 */
@Service
public class StudentFactoryBean extends AbstractFactoryBean<Student> {
    /**
     * 返回要生产的Bean的Class
     *
     * @return Student
     */
    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }

    /**
     * 实现抽像方法,如何创建单例交给子类实现
     * @return
     * @throws Exception
     */
    @Override
    protected Student createInstance() throws Exception {
        Student student = new Student();
        student.setName("david");
        student.setAge(20);
        return student;
    }

    @Override
    public boolean isSingleton() {
        return super.isSingleton();
    }
}