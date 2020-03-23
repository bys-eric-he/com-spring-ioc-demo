package com.spring.ioc.demo.proxy;

import com.alibaba.fastjson.JSON;
import com.spring.ioc.demo.util.SpringTrackUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理，需要注意的是，这里用到的是JDK自带的动态代理，代理对象只能是接口中，不能是类。
 * InvocationHandler接口是proxy代理实例的调用处理程序实现的一个接口，
 * 每一个proxy代理实例都有一个关联的调用处理程序；
 * 在代理实例调用方法时，方法调用被编码分派到调用处理程序的invoke方法。
 *
 * @param <T>
 */
public class ServiceBeanProxy<T> implements InvocationHandler {

    //这是动态代理的好处，被封装的对象是Object类型，接受任意类型的对象
    private T target;

    public ServiceBeanProxy(T target) {
        this.target = target;
    }

    /**
     * @param proxy  代理类代理的真实代理对象com.sun.proxy.$Proxy0
     * @param method 我们所要调用某个对象真实的方法的Method对象
     * @param args   指代代理对象方法传递的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SpringTrackUtils.printTrack("********在这里可以实现一些特定的业务逻辑！*********");
        System.out.println("----------invoke调用前--------->\n" + proxy.getClass().getName());
        proxy.getClass().getName();
        System.out.println(method.getName() + "->" + JSON.toJSONString(args));
        Object result = new Object();

        switch (method.getName()) {
            case "addUser": {
                result = 666;
                break;
            }
            case "getUserCount": {
                result = 1000;
                break;
            }
            case "getUserInfo": {
                result = "{\n" +
                        "\t\\\"userName\\\": \\\"eric.he\\\",\n" +
                        "\t\\\"age\\\": 28,\n" +
                        "\t\\\"sex\\\": \\\"boy\\\"\n" +
                        "}";
                break;
            }

        }
        return result;
    }
}