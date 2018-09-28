package com.plugin.httprequest.processor;

import com.plugin.httprequest.Retrofits;
import com.plugin.httprequest.conversion.HttpReturnConversion;
import com.plugin.httprequest.extendInterceptor.InterceptorPlus;
import com.plugin.httprequest.spring.HttpProperties;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class HTTPProxyInstanceTest {

    private static List<InterceptorPlus> okhttpInterceptor;

    // 目标对象
    private Class target;
    private String properties;


    public HTTPProxyInstanceTest(Class target, String properties){
        this.target = target;
        this.properties = properties;
    }

    /**
     * 获取目标对象的代理对象
     * @return 代理对象
     */
    public Object getProxy() {
        return  Proxy.newProxyInstance(target.getClassLoader(), new Class[]{target}, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
                if(method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }else{
                    if(method.getDeclaringClass() == Object.class) {
                        return method.invoke(this, args);
                    }else{
                        getproperties(properties);
                        Retrofits retrofits = new Retrofits();
                        retrofits.getActualTypeArguments(method);
                        return retrofits.getRetrofit(new HttpReturnConversion(),okhttpInterceptor,target,method,args);
                    }
                }
            }
        });
    }


    private void getproperties(String properties){
        URL u1 = Thread.currentThread().getContextClassLoader().getResource(properties);//同一个包下,还是要包名/文件名
        try {
            if(u1!=null){
                InputStream in = new BufferedInputStream(new FileInputStream(u1.getPath()));
                Properties props = new Properties();
                props.load(in);
                new HttpProperties(props);
            }else{
                throw new IOException("Properties Is Null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
