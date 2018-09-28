package com.plugin.httprequest.retrofitesfactory.converter;


import retrofit2.Converter;
import com.plugin.httprequest.retrofitesfactory.converter.cookie.HeadCookies;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * 用于@head标签的Cookie的扩展处理
 *
 * @author LK
 * @date 2018-04-02 21:39
 */
public class HeadCookiesConverter<T> implements Converter<T, String> {

	private Type type;

	private Annotation[] annotations;

	public HeadCookiesConverter(Type type, Annotation[] annotations) {
		this.type = type;
		this.annotations = annotations;
	}

	/**
	 * 转换输出值
	 * @param value
	 * @return
	 * @throws IOException
	 */
	@Override
	public String convert(T value) throws IOException {
		HeadCookies headCookies = (HeadCookies) value;
		return headCookies.get();
	}
}