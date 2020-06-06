package com.linkkou.httprequest.retrofitesfactory;


import com.linkkou.httprequest.extendAnnotations.Retry;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.linkkou.httprequest.HTTPError;
import com.linkkou.httprequest.HTTPReaponseModel;
import com.linkkou.httprequest.HTTPResponse;
import com.linkkou.httprequest.extendPlugin.HttpConversion;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by LK .
 * 构建Retrofits监听，返回对象结果
 *
 * @author LK
 * @version 1.0
 * @data 2017-12-12 12:14
 */
public class HTTPReaponseCallAdapterFactory extends CallAdapter.Factory {

    /**
     * 自定义转换
     */
    private List<HttpConversion> httpConversion;

    /**
     * 转换类型
     */
    private Type type;

    /**
     * 初始化
     *
     * @param httpConversion
     */
    public HTTPReaponseCallAdapterFactory(List<HttpConversion> httpConversion, Type type) {
        this.httpConversion = httpConversion;
        this.type = type;
    }

    /**
     * 初始化
     *
     * @param httpConversion
     * @return
     */
    public static CallAdapter.Factory create(List<HttpConversion> httpConversion, Type type) {
        return new HTTPReaponseCallAdapterFactory(httpConversion, type);
    }

    /**
     * 获取到重试注解
     *
     * @param annotations
     * @return
     */
    private Retry Retry(Annotation[] annotations) {
        for (int i = 0; i < annotations.length; i++) {
            final Annotation annotation = annotations[i];
            if (annotation.annotationType() == Retry.class) {
                final Retry retry = (Retry) annotation;
                return retry;
            }
        }
        return null;
    }

    /**
     * 执行
     *
     * @param httpRF
     * @param clone
     * @param retry
     * @param max
     * @return
     */
    private HTTPReaponseModel Execute(HTTPReaponseModel httpRF, Call<byte[]> clone, Retry retry, int max) {
        int retryNumber = retry == null ? 0 : retry.retryNumber();
        if (max <= retryNumber) {
            try {
                //@Streaming 场景在同步情况无效,同步返回,Body流是完整的,
                //超大文件会阻塞,直到完成
                Response<byte[]> PR = clone.clone().execute();
                httpRF.setError(HTTPError.OK)
                        .setSuccessful(PR.isSuccessful())
                        .setBody(PR.body())
                        .setHeaders(PR.headers())
                        .setResponse(PR.raw())
                        .setConversion(httpConversion, PR, type);
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                httpRF.setError(HTTPError.SocketTimeout);
                if (retry != null && retry.retryType().SocketTimeout()) {
                    return Execute(httpRF, clone, retry, max + 1);
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                httpRF.setError(HTTPError.Runtime);
                if (retry != null && retry.retryType().RunTime()) {
                    return Execute(httpRF, clone, retry, max + 1);
                }
            } catch (IOException e) {
                e.printStackTrace();
                httpRF.setError(HTTPError.IOException);
                if (retry != null && retry.retryType().CallTime()) {
                    return Execute(httpRF, clone, retry, max + 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                httpRF.setError(HTTPError.OtherException);
                if (retry != null && retry.retryType().Unknown()) {
                    return Execute(httpRF, clone, retry, max + 1);
                }
            }
        }
        return httpRF;
    }


    /**
     * 获取到返回对象
     *
     * @param returnType  返回对象类型
     * @param annotations 注解
     * @param retrofit    对象
     * @return
     */
    @Override
    public CallAdapter<byte[], Object> get(final Type returnType, Annotation[] annotations, Retrofit retrofit) {
        //获取返回类型
        Class<?> rawType = getRawType(returnType);
        //类型对比
        if (rawType == HTTPResponse.class) {
            return new CallAdapter<byte[], Object>() {

                @Override
                public Type responseType() {
                    return returnType;
                }

                @Override
                public Object adapt(Call<byte[]> call) {
                    final Call<byte[]> clone = call.clone();
                    HTTPReaponseModel httpRF = new HTTPReaponseModel();
                    final Retry retry = Retry(annotations);
                    return Execute(httpRF, clone, retry, 0);
                }
            };
        }
        return null;
    }

}
