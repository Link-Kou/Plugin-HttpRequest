package com.plugin.httprequest;

import com.plugin.httprequest.extendInterceptor.*;
import com.plugin.httprequest.ssl.NotSSL;
import com.plugin.httprequest.ssl.UserSSL;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import com.plugin.httprequest.extendPlugin.HttpConversion;
import com.plugin.httprequest.extendPlugin.impl.HttpBaseUrlImpl;
import com.plugin.httprequest.log.HttpLogger;
import com.plugin.httprequest.retrofitesfactory.HTTPReaponseCallAdapterFactory;
import com.plugin.httprequest.retrofitesfactory.HTTPResponseConverterFactory;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by LK .
 * http请求构建
 *
 * @author LK
 * @version 1.0
 * @data 2017-05-19 12:14
 */
public class Retrofits {

    private static Logger logger = LoggerFactory.getLogger(HttpLogger.class);

    private String baseUrl = "";

    private Type type;

    /**
     * 获取对象返回值
     *
     * @param method
     */
    public void getActualTypeArguments(Method method) {
        Type type = method.getGenericReturnType();// 获取返回值类型
        if (type instanceof ParameterizedType) { // 判断获取的类型是否是参数类型
            Type[] typesto = ((ParameterizedType) type).getActualTypeArguments();// 强制转型为带参数的泛型类型，
            if (typesto.length == 1) {
                this.type = typesto[0];
            }
        }
    }

    /**
     * 构建Retrofit
     *
     * @param httpConversion 自定义转换
     * @param classs         代理对象
     * @param methodcall     调用方法
     * @param args           参数值
     * @return
     */
    public HTTPReaponseModel getRetrofit(HttpConversion httpConversion, List<InterceptorPlus> okhttpInterceptor, Class classs, Method methodcall, Object... args) {
        HTTPRequest HTTPRequest = (com.plugin.httprequest.HTTPRequest) classs.getAnnotation(HTTPRequest.class);
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        /*HTTP请求*/
        final OkHttpClient.Builder builder = httpBuilder
                .writeTimeout(HTTPRequest.writeTimeout(), TimeUnit.SECONDS)//秒
                .readTimeout(HTTPRequest.readTimeout(), TimeUnit.SECONDS)
                .connectTimeout(HTTPRequest.connectTimeout(), TimeUnit.SECONDS)
                .addInterceptor(new InterceptorHttpLogging())
                .addInterceptor(new InterceptorCookie(methodcall))
                .addInterceptor(new InterceptorGetBody(methodcall))
                .addInterceptor(new InterceptorConnection());
        if (okhttpInterceptor != null) {
            okhttpInterceptor.stream().filter(Objects::nonNull).forEach(x -> builder.addInterceptor(x.setMethod(methodcall)));
        }
        OkHttpClient okHttpClient = builder.build();
        /**
         * SSL安全连接
         */
        SSL(HTTPRequest, builder, okHttpClient);
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(HTTPResponseConverterFactory.create(httpConversion))
                .addCallAdapterFactory(HTTPReaponseCallAdapterFactory.create(httpConversion, type))
                .baseUrl(new HttpBaseUrlImpl(HTTPRequest).geUrl())//主机地址
                .build();
        try {
            Object Ob = retrofit.create(classs);
            Method[] methods = Ob.getClass().getMethods();
			/*代码予以保留！作为参考依据
			for (Method m : methods) {
				//对象必须要相同
				if(m.getName() == methodcall.getName()){
					method = m;
				}
			}*/
            Method method = methodoverload(methods, methodcall);
            return (HTTPReaponseModel) Objects.requireNonNull(method).invoke(Ob, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HTTPReaponseModel();
    }

    /**
     * SSL连接
     * @param HTTPRequest
     * @param builder
     * @param okHttpClient
     */
    private void SSL(HTTPRequest HTTPRequest, OkHttpClient.Builder builder, OkHttpClient okHttpClient) {
        if (HTTPRequest.NotSSL() && HTTPRequest.SSL()) {
            throw new IllegalAccessError();
        } else {
            //安全请求
            if (HTTPRequest.NotSSL()) {
                NotSSL.SSLNotCheck(okHttpClient);
            }
            if (HTTPRequest.SSL()) {
                UserSSL.UserSSL(builder, HTTPRequest.SSLPassword(), HTTPRequest.SSLfile());
            }
        }
    }

    /**
     * 获取到对应的方法
     *
     * @param methods
     * @param methodcall
     */
    private Method methodoverload(Method[] methods, Method methodcall) {
        for (Method m : methods) {
            if (m.getName().equals(methodcall.getName())) {
                if (m.getParameters().length == methodcall.getParameters().length) {
                    boolean el = false;
                    for (int i = 0; i < m.getParameters().length; i++) {
                        Type parameterizedType = methodcall.getParameters()[i].getParameterizedType();
                        //泛型判断
                        if (parameterizedType instanceof ParameterizedTypeImpl) {
                            Class<?> rawType = ((ParameterizedTypeImpl) methodcall.getParameters()[i].getParameterizedType()).getRawType();
                            parameterizedType = rawType;
                        }
                        if (!m.getParameters()[i].getParameterizedType().equals(parameterizedType)) {
                            el = true;
                            break;
                        }
                    }
                    if (!el) {
                        return m;
                    }
                }
            }
        }
        return null;
    }

}
