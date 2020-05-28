package com.linkkou.httprequest.spring;


import com.linkkou.httprequest.Retrofits;
import com.linkkou.httprequest.extendInterceptor.InterceptorPlus;
import com.linkkou.httprequest.extendPlugin.HttpConversion;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;


/**
 * Created by LK .
 * Bean 注入代理类型
 *
 * @author LK
 * @version 1.0
 * @data 2017-04-22 12:14
 */
/*@Component*/
public class ProxyObjects implements FactoryBean, InitializingBean, DisposableBean {
    /**
     * 代理对象
     */
    private Class serviceClass;

    /**
     * 自定义请求转换
     * 针对@Body进行处理
     */
    private List<HttpConversion> httpConversion;

    /**
     * 自定义拦截器
     *
     * @param okhttpInterceptor
     * @return
     */
    private List<InterceptorPlus> okhttpInterceptor;

    public void setServiceClass(Class serviceClass) {
        this.serviceClass = serviceClass;
    }

    public void setHttpConversion(List<HttpConversion> httpConversion) {
        this.httpConversion = httpConversion;
    }

    public void setOkhttpInterceptor(List<InterceptorPlus> okhttpInterceptor) {
        this.okhttpInterceptor = okhttpInterceptor;
    }

    /**
     * 生成的代理对象
     */
    private Object proxyObj;

    @Override
    public void afterPropertiesSet() throws Exception {
        //返回代理实现
        proxyObj = Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass}, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                } else {
                    Retrofits retrofits = new Retrofits();
                    retrofits.getActualTypeArguments(method);
                    return retrofits.getRetrofit(httpConversion, okhttpInterceptor, serviceClass, method, args);
                }

            }
        });
    }

    @Override
    public Object getObject() throws Exception {
        //返回代理实现
        return proxyObj;
    }

    @Override
    public Class<?> getObjectType() {
        //返回对象，防止Spring无法识别类，能用却报类型不确定
        return serviceClass;
    }

    /**
     * 缓存
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {

    }


}
