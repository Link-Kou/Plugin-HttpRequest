package com.linkkou.httprequest;

import com.linkkou.httprequest.extendInterceptor.*;
import com.linkkou.httprequest.extendPlugin.HttpConversion;
import com.linkkou.httprequest.extendPlugin.impl.HttpBaseUrlImpl;
import com.linkkou.httprequest.log.HttpLogger;
import com.linkkou.httprequest.retrofitesfactory.HTTPReaponseCallAdapterFactory;
import com.linkkou.httprequest.retrofitesfactory.HTTPResponseConverterFactory;
import com.linkkou.httprequest.ssl.NotSSL;
import com.linkkou.httprequest.ssl.UserSSL;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        // 获取返回值类型
        Type type = method.getGenericReturnType();
        // 判断获取的类型是否是参数类型
        if (type instanceof ParameterizedType) {
            // 强制转型为带参数的泛型类型，
            Type[] typesto = ((ParameterizedType) type).getActualTypeArguments();
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
    public Object getRetrofit(List<HttpConversion> httpConversion, List<InterceptorPlus> okhttpInterceptor, Class classs, Method methodcall, Object... args) throws InvocationTargetException, IllegalAccessException {
        HTTPRequest HTTPRequest = (com.linkkou.httprequest.HTTPRequest) classs.getAnnotation(HTTPRequest.class);
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        /**
         * HTTP请求
         * SECONDS:秒
         */
        final OkHttpClient.Builder builder = httpBuilder
                //允许失败重试
                .retryOnConnectionFailure(true)
                .writeTimeout(HTTPRequest.writeTimeout(), TimeUnit.SECONDS)
                .readTimeout(HTTPRequest.readTimeout(), TimeUnit.SECONDS)
                .connectTimeout(HTTPRequest.connectTimeout(), TimeUnit.SECONDS)
                .callTimeout(HTTPRequest.callTimeout(), TimeUnit.SECONDS)
                .addInterceptor(new InterceptorCookie(methodcall))
                .addInterceptor(new InterceptorConnection());
        //添加自定义拦截器
        if (okhttpInterceptor != null) {
            okhttpInterceptor.stream().filter(Objects::nonNull).forEach(x -> builder.addInterceptor(x.setMethod(methodcall)));
        }
        //添加日志与重试连接器
        //OKHttp在执行调用拦截器以List有序调用，在拦截器执行请求后续再执行请求会发送多次请求
        builder.addInterceptor(new InterceptorHttpLogging(methodcall))
        ;


        OkHttpClient okHttpClient = builder.build();
        /**
         * SSL安全连接
         */
        SSL(HTTPRequest, builder, okHttpClient);
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                /**
                 * 对注解解析进行自定义转换
                 */
                .addConverterFactory(HTTPResponseConverterFactory.create(httpConversion))
                /**
                 * 请求结果转换的自定义返回参数
                 */
                .addCallAdapterFactory(HTTPReaponseCallAdapterFactory.create(httpConversion, type))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
                //主机地址
                .baseUrl(new HttpBaseUrlImpl(HTTPRequest).geUrl())
                .build();

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
        return Objects.requireNonNull(method).invoke(Ob, args);
    }

    /**
     * SSL连接设置
     *
     * @param HTTPRequest
     * @param builder
     * @param okHttpClient
     */
    private void SSL(HTTPRequest HTTPRequest, OkHttpClient.Builder builder, OkHttpClient okHttpClient) {
        final com.linkkou.httprequest.HTTPRequest.SSL.SSLConfig ssl = HTTPRequest.ssl().SSL();
        switch (ssl) {
            case NOTSSL:
                NotSSL.SSL(okHttpClient);
                return;
            case OWNSSL:
                UserSSL.SSL(okHttpClient, HTTPRequest.ssl().SSLPassword(), HTTPRequest.ssl().SSLfile());
                return;
            case DEFAULT:
            default:
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
