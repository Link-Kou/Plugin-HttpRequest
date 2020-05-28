package com.linkkou.httprequest.retrofitesfactory;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Converter.Factory;
import retrofit2.Retrofit;
import com.linkkou.httprequest.HTTPResponse;
import com.linkkou.httprequest.retrofitesfactory.converter.RequestBodyConverter;
import com.linkkou.httprequest.extendPlugin.HttpConversion;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by LK .
 * 返回对象判断与Body支持
 *
 * @author LK
 * @version 1.0
 * @data 2017-05-21 14:12
 */
public class HTTPResponseConverterFactory extends Factory {

    private List<HttpConversion> httpConversion;

    public static HTTPResponseConverterFactory create(List<HttpConversion> httpConversion) {
        return new HTTPResponseConverterFactory(httpConversion);
    }

    private HTTPResponseConverterFactory(List<HttpConversion> httpConversion) {
        this.httpConversion = httpConversion;
    }

    /**
     * @param type
     * @param parameterAnnotations
     * @param methodAnnotations
     * @param retrofit
     * @return
     * @body需要解析
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        /**
         * MultipartBody 为内置类型，不走此方法
         */
        for (Annotation annotation : parameterAnnotations) {
            if (annotation.annotationType().equals(retrofit2.http.Body.class)) {
                /**
                 * 相关实现
                 * {@link RequestBodyConverter}
                 */
                return new RequestBodyConverter(httpConversion, methodAnnotations, type);
            }
        }
        return null;
    }

    /**
     * 其他自定义参数
     *
     * @param type        对象类型
     * @param annotations 注解
     * @param retrofit    对象
     * @return
     */
    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            //Header的注解
            if (annotation.annotationType().equals(retrofit2.http.Header.class)) {

            }
        }
        return null;
    }

    /**
     * 判断返回的对象是否合法
     *
     * @param type
     * @param annotations
     * @param retrofit
     * @return
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == HTTPResponse.class) {
            return HTTPResponseConverter.INSTANCE;
        }
        if (((ParameterizedTypeImpl) type).getRawType() == HTTPResponse.class) {
            return HTTPResponseConverter.INSTANCE;
        }
        //其它类型我们不处理，继续错误输出
        try {
            throw new IllegalAccessException("返回结果非HTTPResponse异常");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;
        }

    }
}
