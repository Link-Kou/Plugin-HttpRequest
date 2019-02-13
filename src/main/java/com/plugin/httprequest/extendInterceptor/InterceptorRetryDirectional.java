package com.plugin.httprequest.extendInterceptor;

import com.plugin.httprequest.HTTPRequest;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Optional;


/**
 * 重试与重定向
 *
 * @author lk
 * @date 2018/9/23 09:23
 */
public class InterceptorRetryDirectional implements Interceptor {

    /**
     * 最大重试次数
     */
    private int MAXRETRY;

    /**
     * 是否启用
     */
    private boolean RETRYFAILURE;

    /**
     * 重试与重定向配置
     */
    private HTTPRequest.Retry RETRY;

    /**
     * 假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
     */
    private int RETRYNUM = 0;

    /**
     * 重试与重定向
     *
     * @param retry 重试与重定向配置
     */
    public InterceptorRetryDirectional(HTTPRequest.Retry retry) {
        if (retry.retryNumber() < 0) {
            this.MAXRETRY = 0;
        } else {
            this.MAXRETRY = retry.retryNumber();
        }
        this.RETRYFAILURE = retry.retryFailure();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (this.RETRYFAILURE) {
            Request request = getRetryUrl(chain.request());
            Response response = chain.proceed(request);
            while (!response.isSuccessful() && RETRYNUM < MAXRETRY) {
                RETRYNUM++;
                response = chain.proceed(request);
            }
            return response;
        }
        return chain.proceed(chain.request());
    }

    /**
     * 重定向URL
     *
     * @param request
     * @return
     */
    private Request getRetryUrl(Request request) {
        final Value value1 = RETRY.retryUrl();
        Request newRequest = null;
        if (RETRYNUM > 0) {
            if (!StringUtils.isNotEmpty(value1.value())) {
                final String value = value1.value();
                newRequest = request.newBuilder().url(value).build();
            }
        }
        return Optional.ofNullable(newRequest)
                .orElse(request);
    }

}

