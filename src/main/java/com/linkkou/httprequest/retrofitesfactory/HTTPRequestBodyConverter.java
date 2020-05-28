package com.linkkou.httprequest.retrofitesfactory;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

import java.io.IOException;


/**
 * Created by LK .
 * HTTP @Body 请求类型处理
 * @author LK
 * @version 1.0
 * @data 2017-06-28 12:14
 */
public class HTTPRequestBodyConverter<T> implements Converter<T, RequestBody> {

	static final HTTPRequestBodyConverter<Object> INSTANCE = new HTTPRequestBodyConverter();
	private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain; charset=UTF-8");

	@Override
	public RequestBody convert(T t) throws IOException {
		return RequestBody.create(MEDIA_TYPE, String.valueOf(t));
	}
}
