package com.plugin.httprequest.retrofitesfactory;


import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.plugin.httprequest.HTTPError;
import com.plugin.httprequest.HTTPReaponseModel;
import com.plugin.httprequest.HTTPResponse;
import com.plugin.httprequest.extendPlugin.HttpConversion;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

/**
 * Created by LK .
 * 构建Retrofits监听，返回对象结果
 * @author LK
 * @version 1.0
 * @data 2017-12-12 12:14
 */
public class HTTPReaponseCallAdapterFactory extends CallAdapter.Factory {

	/**
	 * 自定义转换
	 */
	private HttpConversion httpConversion;

	/**
	 * 转换类型
	 */
	private Type type;

	/**
	 * 初始化
	 * @param httpConversion
	 */
	public HTTPReaponseCallAdapterFactory(HttpConversion httpConversion, Type type){
		this.httpConversion = httpConversion;
		this.type=type;
	}

	/**
	 * 初始化
	 * @param httpConversion
	 * @return
	 */
	public static CallAdapter.Factory create(HttpConversion httpConversion, Type type) {
		return new HTTPReaponseCallAdapterFactory(httpConversion,type);
	}

	/**
	 * 获取到返回对象
	 * @param returnType 返回对象类型
	 * @param annotations 注解
	 * @param retrofit 对象
	 * @return
	 */
	@Override
	public CallAdapter<byte[],Object> get(final Type returnType, Annotation[] annotations, Retrofit retrofit) {
		//获取返回类型
		Class<?> rawType = getRawType(returnType);
		//类型对比
		if (rawType == HTTPResponse.class) {
			return new CallAdapter<byte[],Object>() {

				@Override
				public Type responseType() {
					return returnType;
				}

				@Override
				public Object adapt(Call<byte[]> call) {
					HTTPReaponseModel httpRF = new HTTPReaponseModel();
					try {
						Response<byte[]> PR = call.execute();
						httpRF.setError(HTTPError.OK)
								.setSuccessful(PR.isSuccessful())
								.setBody(PR.body())
								.setHeaders(PR.headers())
								.setResponse(PR.raw())
								.setConversion(httpConversion,PR,type);
					} catch (SocketTimeoutException e) {
						e.printStackTrace();
						return httpRF.setError(HTTPError.SocketTimeout);
					} catch (RuntimeException e) {
						e.printStackTrace();
						return httpRF.setError(HTTPError.Runtime);
					} catch (IOException e) {
						e.printStackTrace();
						return httpRF.setError(HTTPError.IOException);
					}
					return httpRF;
				}
			};
		}
		try {
			throw new IllegalAccessException("返回结果非HTTPResponse异常");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return null;
		}
	}

}
