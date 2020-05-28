package com.linkkou.httprequest.extendInterceptor;

import okhttp3.Interceptor;

import java.lang.reflect.Method;

/**
 * 自定义扩展拦截器
 *
 * @author lk
 * @date 2018/9/23 09:23
 */
public abstract class InterceptorPlus implements Interceptor {

    private Method methodcall;

    /**
     * 执行
     *
     * @param methodcall
     * @return
     */
    public InterceptorPlus setMethod(Method methodcall) {
        this.methodcall = methodcall;
        return this;
    }

}

