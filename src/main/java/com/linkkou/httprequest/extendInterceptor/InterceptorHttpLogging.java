package com.linkkou.httprequest.extendInterceptor;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import com.linkkou.httprequest.log.HttpLogger;

import java.io.IOException;

/**
 * HTTP日志拦截
 * */
public class InterceptorHttpLogging implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS).setLevel(HttpLoggingInterceptor.Level.BODY);
        return logInterceptor.intercept(chain);
    }
}
