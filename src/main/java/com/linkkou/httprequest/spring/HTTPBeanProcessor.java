package com.linkkou.httprequest.spring;


import com.linkkou.httprequest.HTTPRequest;
import com.linkkou.httprequest.extendInterceptor.InterceptorPlus;
import com.linkkou.httprequest.extendPlugin.HttpConversion;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Created by LK .
 * Bean @Autowire 具体实现类
 *
 * @author LK
 * @version 1.0
 * @data 2017-04-23 12:14
 */
public class HTTPBeanProcessor extends PropertyPlaceholderConfigurer implements BeanDefinitionRegistryPostProcessor {


    private String[] prefix;

    private List<HttpConversion> httpConversion;

    private List<InterceptorPlus> okhttpInterceptor;

    public void setPrefix(String[] prefix) {
        this.prefix = prefix;
    }

    /**
     * 自定义转换类
     *
     * @param httpConversion
     */
    public void setHttpConversion(List<HttpConversion> httpConversion) {
        this.httpConversion = httpConversion;
    }

    /**
     * 自定义拦截器
     *
     * @param okhttpInterceptor
     * @return
     */
    public HTTPBeanProcessor setOkhttpInterceptor(List<InterceptorPlus> okhttpInterceptor) {
        this.okhttpInterceptor = okhttpInterceptor;
        return this;
    }


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //谷歌元反射
        Reflections reflections = new Reflections(prefix);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(HTTPRequest.class);
        for (Class<?> serviceClass : annotated) {
            for (Annotation annotation : serviceClass.getAnnotations()) {
                //自定义注解RetrofitService，都需要通过retrofit创建bean
                if (annotation instanceof HTTPRequest) {
                    RootBeanDefinition beanDefinition = new RootBeanDefinition();
                    //注入代理
                    beanDefinition.setBeanClass(ProxyObjects.class);
                    //是否惰性加载
                    beanDefinition.setLazyInit(false);
                    //是否自动注入
                    beanDefinition.setAutowireCandidate(true);
                    /**
                     * 设置bean里变量
                     * {@link  ProxyObjects}
                     */
                    beanDefinition.getPropertyValues().addPropertyValue("serviceClass", serviceClass);
                    beanDefinition.getPropertyValues().addPropertyValue("httpConversion", httpConversion);
                    beanDefinition.getPropertyValues().addPropertyValue("okhttpInterceptor", okhttpInterceptor);
                    /**
                     * serviceClass.getName() 获取Class命名空间路径
                     * 保证在一个类中注入多次，能够完全独立开放
                     */
                    registry.registerBeanDefinition(serviceClass.getName(), beanDefinition);

                }
            }
        }

    }

    /**
     * 调用就会触发
     * 这里提供了修改beanFacotry的机会
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
    }

    /**
     * 获取properties配置
     *
     * @param locations
     */
    @Override
    public void setLocations(Resource... locations) {
        loadProps(locations);
        super.setLocations(locations);
    }

    /**
     * 配置文件，路径
     * 注意：多个配置文件功能暂时不予以支持
     * BUG：未添加多个配置文件输入的报错
     *
     * @param name
     */
    private synchronized void loadProps(Resource... name) {
        Properties props = new Properties();
        try {
            props.load(name[0].getInputStream());
            new HttpProperties(props);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
