package com.Spring.interceptor;

import com.linkkou.httprequest.extendInterceptor.InterceptorPlus;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 自定义添加Cookie
 *
 * @author lk
 * @version 1.0
 * @date 2019/4/28 17:59
 */
public class InterceptorCookiePlus extends InterceptorPlus {


    @Override
    public Response intercept(Chain chain) throws IOException {
        String cookie = chain.request().header("Cookie");
        if (cookie != null && !cookie.endsWith(";")) {
            cookie += ";";
        }
        Request.Builder Builder = chain.request()
                .newBuilder();
        Builder.removeHeader("Cookie");
        Builder.addHeader("Cookie", "token=123");
        return chain.proceed(Builder.build());
    }

}
