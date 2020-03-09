package com.spring.ioc.demo.processor;

import com.spring.ioc.demo.factory.ServiceFactoryBean;
import com.spring.ioc.demo.service.impl.CalculateServiceImpl;
import com.spring.ioc.demo.util.SpringTrackUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 注册Bean到Spring容器, Bean后处理器,为接口注入实现类
 * 使用BeanDefinitionRegistryPostProcessor动态注入BeanDefinition
 * Spring提供了很多Aware接口, 比如BeanFactoryAware、 ApplicationContextAware、ResourceLoaderAware、 ServletContextAware等等.
 * 这些接口一般都有个setXXX来设置对应的组件. 如果我们的Bean实现了这些Aware的时候就可以获取对应的资源.
 */
@Component
public class CustomizeBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, ResourceLoaderAware, ApplicationContextAware {
    private static final String DEFAULT_RESOURCE_PATTERN = "**/UserService.class";
    private static ApplicationContext applicationContext;
    private MetadataReaderFactory metadataReaderFactory;
    private ResourcePatternResolver resourcePatternResolver;

    /**
     * 注入ApplicationContext实例
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 这个方法来自 BeanDefinitionRegistryPostProcessor
     * * BeanDefinitionRegistryPostProcessor的postProcessBeanDefinitionRegistry方法在Bean被定义但还没被创建的时候执行.
     * * 即实现postProcessBeanDefinitionRegistry方法，可以修改增加BeanDefinition。
     * * 此特性可以用来动态生成bean，比如读取某个配置项，然后根据配置项动态生成bean。
     *
     * @param beanDefinitionRegistry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        //打印当前堆栈信息
        SpringTrackUtils.printTrack("CustomizeBeanDefinitionRegistryPostProcessor ->execute postProcessBeanDefinitionRegistry");

        Set<Class<?>> clazzSet = scannerPackages("com.spring.ioc.demo.service");
        clazzSet.stream().filter(Class::isInterface).forEach(x -> registerBean(beanDefinitionRegistry, x));

        //创建一个bean的定义类的对象，bean类型是CalculateServiceImpl
        RootBeanDefinition calculateService = new RootBeanDefinition(CalculateServiceImpl.class);

        //bean的定义注册到spring环境, 这里要注意和@Service("calculateService")的名称要区分开,否则在注入时会报bean的名称已经存在的错误。
        beanDefinitionRegistry.registerBeanDefinition("calculateService-bean", calculateService);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        //打印当前堆栈信息
        SpringTrackUtils.printTrack("CustomizeBeanDefinitionRegistryPostProcessor -> execute postProcessBeanFactory");
    }

    /**
     * 获取资源加载器,使用ResourceLoaderAware加载外部资源,这里用于加载*.class文件。
     * Spring ResourceLoader为我们提供了一个统一的getResource()方法来通过资源路径检索外部资源。
     *
     * @param resourceLoader
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    private void registerBean(BeanDefinitionRegistry registry, Class clazz) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        definition.getConstructorArgumentValues().addGenericArgumentValue(clazz);
        definition.setBeanClass(ServiceFactoryBean.class);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        registry.registerBeanDefinition(clazz.getSimpleName(), definition);
    }

    /**
     * 获取指定路径及子路径下的所有类
     */
    private Set<Class<?>> scannerPackages(String basePackage) {
        Set<Class<?>> set = new LinkedHashSet<>();
        String basePackageName = ClassUtils.convertClassNameToResourcePath(applicationContext.getEnvironment().resolveRequiredPlaceholders(basePackage));

        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                basePackageName + '/' + DEFAULT_RESOURCE_PATTERN;
        try {
            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    Class<?> clazz;
                    try {
                        clazz = Class.forName(className);
                        set.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }
}