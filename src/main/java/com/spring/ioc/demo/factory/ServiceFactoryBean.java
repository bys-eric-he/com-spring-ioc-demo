package com.spring.ioc.demo.factory;

import com.spring.ioc.demo.proxy.ServiceBeanProxy;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Spring为此提供了一个org.springframework.bean.factory.FactoryBean的工厂类接口，
 * 用户可以通过实现该接口定制实例化Bean的逻辑。FactoryBean接口对于Spring框架来说占用重要的地位，
 * Spring自身就提供了70多个FactoryBean的实现。它们隐藏了实例化一些复杂Bean的细节，给上层应用带来了便利。
 * 从Spring3.0开始，FactoryBean开始支持泛型，即接口声明改为FactoryBean<T>的形式
 * <p>
 * FactoryBean是一个工厂Bean，可以生成某一个类型Bean实例，它最大的一个作用是：可以让我们自定义Bean的创建过程。
 *
 * @param <T>
 */
public class ServiceFactoryBean<T> implements FactoryBean<T> {

    //要代理的真实对象
    private Class<T> interfaceType;

    /**
     * 实现了FactoryBean接口的bean是一类叫做factory的bean。
     * 其特点是，spring会在使用getBean()调用获得该bean时，会自动调用该bean的getObject()方法，
     * 所以返回的不是ServiceFactory这个bean的实例，而是这个bean.getObject()方法的返回值。
     *
     * @param interfaceType
     */
    public ServiceFactoryBean(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    /**
     * 返回由FactoryBean创建的Bean实例，
     * 如果isSingleton()返回true，则该实例会放到Spring容器中单实例缓存池中
     *
     * @return 返回由FactoryBean创建的Bean实例
     */
    @Override
    public T getObject() {
        //代理对象的调用处理程序，我们将要代理的真实对象传入代理对象的调用处理的构造函数中，最终代理对象的调用处理程序会调用真实对象的方法
        InvocationHandler handler = new ServiceBeanProxy<>(interfaceType);

        //返回指定接口的代理类的实例，该接口将方法调用分派给指定的调用处理程序。
        //  通过Proxy类的newProxyInstance方法创建代理对象，我们来看下方法中的参数
        //  第一个参数：interfaceType.getClass().getClassLoader()，使用handler对象的classloader对象来加载我们的代理对象
        //  第二个参数：interfaceType.getClass().getInterfaces()，这里为代理类提供的接口是真实对象实现的接口，这样代理对象就能像真实对象一样调用接口中的所有方法
        //  第三个参数：handler，我们将代理对象关联到上面的InvocationHandler对象上
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(),
                new Class[]{interfaceType}, handler);
    }

    /**
     * 返回FactoryBean创建的Bean类型。
     *
     * @return Bean类型
     */
    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}