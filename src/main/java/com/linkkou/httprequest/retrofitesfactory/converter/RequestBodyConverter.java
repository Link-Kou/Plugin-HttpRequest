package com.linkkou.httprequest.retrofitesfactory.converter;

import com.linkkou.httprequest.extendAnnotations.Convert;
import com.linkkou.httprequest.extendPlugin.HttpConversion;
import com.linkkou.httprequest.extendPlugin.impl.HttpReturnJsonConversion;
import com.linkkou.httprequest.spring.ProxyObjects;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 用于 @body 自定义转换
 *
 * @author LK
 * @version 1.0
 * @data 2017-12-22 17:52
 */
public class RequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/serialization; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private HttpConversion httpConversion;
    private final Type adapter;
    private Annotation[] methodAnnotations;

    public RequestBodyConverter(List<HttpConversion> httpConversion, Annotation[] methodAnnotations, Type adapter) {
        this.httpConversion = Convert(httpConversion, methodAnnotations);
        this.adapter = adapter;
        this.methodAnnotations = methodAnnotations;
    }

    private HttpConversion Convert(List<HttpConversion> httpConversions, Annotation[] methodAnnotations) {
        if (httpConversions != null && httpConversions.size() > 0) {
            for (Annotation annotation : methodAnnotations) {
                if (annotation.annotationType().equals(Convert.class)) {
                    final Convert convert = (Convert) annotation;
                    for (HttpConversion httpConversion : httpConversions) {
                        if (httpConversion.getType().equals(convert.value())) {
                            return httpConversion;
                        }
                    }
                }
            }
            return httpConversions.get(0);
        }
        /**
         * 默认返回GSON的转换器
         */
        return new HttpReturnJsonConversion();
    }

    @Override
    public RequestBody convert(T t) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        /**
         * 扩展接口
         * {@link ProxyObjects#httpConversion}
         */
        httpConversion.bodyWriter(writer, t, adapter);
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }

}