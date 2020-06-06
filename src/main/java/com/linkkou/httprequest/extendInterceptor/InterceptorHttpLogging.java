package com.linkkou.httprequest.extendInterceptor;

import com.linkkou.httprequest.extendAnnotations.Log;
import com.linkkou.httprequest.log.HttpLogger;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * HTTP日志拦截
 */
public class InterceptorHttpLogging implements Interceptor {

    private final boolean log;

    public InterceptorHttpLogging(Method methodcall) {
        Log log = methodcall.getAnnotation(Log.class);
        if (log != null) {
            this.log = log.value();
        } else {
            this.log = true;
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (this.log) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS).setLevel(HttpLoggingInterceptor.Level.BODY);
            return logInterceptor.intercept(chain);
        }
        return chain.proceed(chain.request());
    }
}
