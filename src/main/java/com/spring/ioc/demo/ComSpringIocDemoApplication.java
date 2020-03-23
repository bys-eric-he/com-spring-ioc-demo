package com.spring.ioc.demo;

import com.alibaba.fastjson.JSON;
import com.spring.ioc.demo.aware.CustomizeBeanFactoryAware;
import com.spring.ioc.demo.aware.SpringApplicationContextAware;
import com.spring.ioc.demo.bean.User;
import com.spring.ioc.demo.config.UserBeanConfig;
import com.spring.ioc.demo.proxy.StudentServiceProxy;
import com.spring.ioc.demo.service.CalculateService;
import com.spring.ioc.demo.service.StudentService;
import com.spring.ioc.demo.service.impl.StudentServiceImpl;
import com.spring.ioc.demo.util.SerializationUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@SpringBootApplication
public class ComSpringIocDemoApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ComSpringIocDemoApplication.class, args);

            // 使用ClassPathXmlApplicationContext获取spring容器ApplicationContext
            // 从类路径下的一个或多个xml配置文件中加载上下文定义，适用于xml配置的方式。
            AbstractApplicationContext applicationContext1 = new ClassPathXmlApplicationContext("beans.xml");
            // 根据bean id获取bean对象
            User user = (User) applicationContext1.getBean("user");
            System.out.println(JSON.toJSONString(user));

            // 使用AnnotationConfigApplicationContext获取spring容器ApplicationContext2
            // 从一个或多个基于java的配置类中加载上下文定义，适用于java注解的方式。
            AbstractApplicationContext applicationContext2 = new AnnotationConfigApplicationContext(UserBeanConfig.class);
            User user2 = applicationContext2.getBean(User.class);

            System.out.println(JSON.toJSONString(user2));

            User user3 = applicationContext2.getBean(User.class);

            System.out.println("user is equals to user2 ? " + user.equals(user2));

            System.out.println("user2 is equals to user3 ? " + user2.equals(user3));

            SerializationUtils.SerializeObject(user2);

            String[] namesForType = applicationContext2.getBeanNamesForType(User.class);
            for (String name : namesForType) {
                System.out.println("applicationContext2上下文中 bean名称为->" + name);
            }

            CalculateService calculateService = (CalculateService) SpringApplicationContextAware.getBean("calculateService-bean");
            System.out.println("1+1=" + calculateService.add(1, 1));
            System.out.println(calculateService.getServiceDesc());

            CalculateService calculateServiceBean = (CalculateService) CustomizeBeanFactoryAware.getBean("calculateService-bean");
            System.out.println("8+8=" + calculateServiceBean.add(8, 8));
            calculateServiceBean.setServiceDesc("CalculateServiceImpl -> desc from calculate service has been changed by change by calculateService-bean");
            System.out.println(calculateServiceBean.getServiceDesc());

            // 手动执行close方法
            // 在非Web应用中，手工加载Spring IoC容器, 如果需要手动关闭容器, 不能用ApplicationContext, 要用AbstractApplicationContext。
            // applicationContext.close()关闭容器。
            // applicationContext2.close();

            User deserializeUser = SerializationUtils.DeserializeObject();

            System.out.println(JSON.toJSONString(deserializeUser));

            //FactoryBean生成的实例
            System.out.println("--------FactoryBean生成的实例-----------");
            System.out.println(SpringApplicationContextAware.getBean("studentFactoryBean"));
            System.out.println(SpringApplicationContextAware.getBean("&studentFactoryBean"));



            StudentService studentService=new StudentServiceImpl();
            InvocationHandler studentServiceProxy=new StudentServiceProxy(studentService);
            StudentService studentServiceProxyInstance= (StudentService) Proxy.newProxyInstance(studentService.getClass().getClassLoader(), studentService.getClass().getInterfaces(), studentServiceProxy);
            //参数一：proxyInstance类的类加载器
            //参数二：proxyInstance实现的所有接口
            //参数三：实现了invocationHandler接口的对象

            studentServiceProxyInstance.buy();
            studentServiceProxyInstance.talk();

        } catch (Exception exception) {
            System.out.println("##########服务启动异常#########");
            exception.printStackTrace();
        }


    }

}
