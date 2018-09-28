package com.plugin.httprequest.extendInterceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 解决特殊情况下的，半链接问题
 * @author LK
 * @version 1.0
 * @data 2017-07-12 17:34
 *
 */
public class InterceptorConnection implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder Builder = chain.request()
                .newBuilder()
                .addHeader("Connection", "close");
        return chain.proceed(Builder.build());
    }

}
