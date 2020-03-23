package com.spring.ioc.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Student行为代理类，根据传的InvocationHandler参数，
 * 在代理类的实现接口的方法里面实现InvocationHandler的invoke()方法，
 * 也就是调用InvocationHandler实现类的invoke方法，
 * 然后在InvocationHandler实现类的InvocationHandler方法里加入业务逻辑，
 * 中间在再通过invoke方法调用被代理类的方法
 */
public class StudentServiceProxy implements InvocationHandler {
    /**
     * 这是动态代理的好处，被封装的对象是Object类型，接受任意类型的对象
     */
    private Object obj;

    public StudentServiceProxy(Object obj) {
        this.obj = obj;
    }

    /**
     * invoke方法中的第一个参数proxy的用途
     * 1. 可以使用反射获取代理对象的信息（也就是proxy.getClass().getName()）。
     * 2. 可以将代理对象返回以进行连续调用，这就是proxy存在的目的，因为this并不是代理对象。
     *
     * @param proxy  代理类代理的真实代理对象com.sun.proxy.$Proxy0
     * @param method 我们所要调用某个对象真实的方法的Method对象
     * @param args   指代代理对象方法传递的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("talk")) {
            System.out.println("--------------- Invoke 增强方法 " + method.getName() + "--------------");

        } else {
            System.out.println("--------------- Invoke 普通方法 " + method.getName() + "--------------");
        }
        method.invoke(obj, args);

        System.out.println("-------" + proxy.getClass().getName() + " StudentServiceProxy Invoke End-----------");

        return proxy;
    }
}