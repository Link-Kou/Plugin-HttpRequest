package com.plugin.httprequest.extendInterceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import com.plugin.httprequest.extendAnnotations.cookie.Cookie;
import com.plugin.httprequest.extendAnnotations.cookie.Cookies;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Cookie拦截器
 * {@link Cookies}
 *
 * @author LK
 * @version 1.0
 * @data 2017-07-12 17:34
 */
public class InterceptorCookie implements Interceptor {

    private String CookieVal;

    private Boolean isCookie = false;

    public InterceptorCookie(Method methodcall) {
        if (methodcall != null) {
            Cookies Cookies = methodcall.getAnnotation(Cookies.class);
            if (Cookies != null) {
                isCookie = true;
                loadAnnotationsField(Cookies);
            }
        }
    }

    /**
     * 获取Cookie内容,构建Cookie格式
     *
     * @param Cookies
     */
    private void loadAnnotationsField(Cookies Cookies) {
        if (Cookies != null) {
            StringBuilder stringBuilder = new StringBuilder();
            Cookie[] Cookievalues = Cookies.value();
            for (Cookie cookie : Cookievalues) {
                stringBuilder.append(cookie.key());
                stringBuilder.append("=");
                stringBuilder.append(cookie.value());
                stringBuilder.append(";");
            }
            String d = stringBuilder.toString();
            CookieVal = d.substring(0, d.length() - 1);
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(isCookie){
            String cookie = chain.request().header("Cookie");
            if (cookie != null && !cookie.endsWith(";")) {
                cookie += ";";
            }
            Request.Builder Builder = chain.request()
                    .newBuilder();
            Builder.removeHeader("Cookie");
            if (CookieVal != null && CookieVal.length() > 0) {
                Builder.addHeader("Cookie", cookie == null ? CookieVal : cookie + CookieVal);
            }
            return chain.proceed(Builder.build());
        }
        return chain.proceed(chain.request());
    }
}
