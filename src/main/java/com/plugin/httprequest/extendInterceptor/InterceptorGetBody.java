package com.plugin.httprequest.extendInterceptor;

import com.plugin.httprequest.extendAnnotations.GetBody;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * HTTP Get发送的Body由于框架的本身的限制发送后无法被Spring正常接收
 * @author lk
 * @date 2018/9/20 11:19
 */
@Deprecated
public class InterceptorGetBody implements Interceptor {

    private boolean getbody = false;

    /**
     * HTTP Get发送的Body由于框架的本身的限制发送后无法被Spring正常接收
     * @param methodcall
     */
    public InterceptorGetBody(Method methodcall) {
        if (methodcall != null) {
            GetBody getBody = methodcall.getAnnotation(GetBody.class);
            if (getBody != null) {
                this.getbody = true;
            }
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();
        Request.Builder Builder = request.newBuilder();
        if(getbody){
            Class<?> cls= Builder.getClass();
            try {
                Field f = cls.getDeclaredField("method");
                //为 true 则表示反射的对象在使用时取消 Java 语言访问检查
                f.setAccessible(true);
                f.set(Builder,"GET");
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return chain.proceed(Builder.build());
        }
        return chain.proceed(Builder.build());
    }
}
