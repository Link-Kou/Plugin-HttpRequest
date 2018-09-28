package com.plugin.httprequest.retrofitesfactory;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Converter.Factory;
import retrofit2.Retrofit;
import com.plugin.httprequest.HTTPResponse;
import com.plugin.httprequest.retrofitesfactory.converter.GsonRequestBodyConverter;
import com.plugin.httprequest.retrofitesfactory.converter.cookie.HeadCookies;
import com.plugin.httprequest.extendPlugin.HttpConversion;
import com.plugin.httprequest.retrofitesfactory.converter.HeadCookiesConverter;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by LK .
 * 返回对象判断与Body支持
 * @author LK
 * @version 1.0
 * @data 2017-05-21 14:12
 */
public class HTTPResponseConverterFactory extends Factory {

	private HttpConversion httpConversion;

	public static HTTPResponseConverterFactory create(HttpConversion httpConversion) {
		return new HTTPResponseConverterFactory(httpConversion);
	}

	private HTTPResponseConverterFactory(HttpConversion httpConversion) {
		this.httpConversion = httpConversion;
	}

	/**
	 * @body需要解析
	 * @param type
	 * @param parameterAnnotations
	 * @param methodAnnotations
	 * @param retrofit
	 * @return
	 */
	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		/**
		 * MultipartBody 为内置类型，不走此方法
		 */
		for(Annotation annotation : parameterAnnotations){
			if(annotation.annotationType().equals(retrofit2.http.Body.class)){
				return new GsonRequestBodyConverter(httpConversion,methodAnnotations,type);
			}
		}
		return null;
	}

	/**
	 * 其他自定义参数
	 * @param type
	 * @param annotations
	 * @param retrofit
	 * @return
	 */
	@Override
	public  Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		for(Annotation annotation : annotations){
			//Header的注解
			if(annotation.annotationType().equals(retrofit2.http.Header.class)){
				if(type.equals(HeadCookies.class)){
					return new HeadCookiesConverter(type,annotations);
				};
			};
		}
		return null;
	}

	/**
	 * 判断返回的对象是否合法
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
		if(((ParameterizedTypeImpl) type).getRawType() == HTTPResponse.class){
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
