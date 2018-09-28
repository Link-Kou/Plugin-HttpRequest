package com.plugin.httprequest.retrofitesfactory.converter;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;
import com.plugin.httprequest.conversion.HttpReturnConversion;
import com.plugin.httprequest.extendPlugin.HttpConversion;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * 用于 @body 自定义转换
 * @author LK
 * @version 1.0
 * @data 2017-12-22 17:52
 */
public class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

	private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private HttpConversion httpConversion = new HttpReturnConversion();
	private final Type adapter;
	private Annotation[] methodAnnotations;

	public GsonRequestBodyConverter(HttpConversion httpConversion, Annotation[] methodAnnotations, Type adapter) {
		this.httpConversion = httpConversion;
		this.adapter = adapter;
		this.methodAnnotations = methodAnnotations;
	}

	@Override
	public RequestBody convert(T t) throws IOException {
		Buffer buffer = new Buffer();
		Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
		/**
		 * 扩展接口
		 * {@link com.plugin.httprequest.spring.ProxyObjects#httpConversion}
		 */
		httpConversion.bodyWriter(writer,t,adapter);
		return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
	}

}