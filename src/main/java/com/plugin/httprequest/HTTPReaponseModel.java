package com.plugin.httprequest;

import com.plugin.httprequest.conversion.HttpReturnConversion;
import okhttp3.Headers;
import okhttp3.Response;
import com.plugin.httprequest.extendPlugin.HttpConversion;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by LK .
 * 非公开返回实体对象
 * @author LK
 * @version 1.0
 * @data 2017-05-20 12:14
 */
public class HTTPReaponseModel implements HTTPResponse<Object> {

	/**
	 * 错误信息
	 */
	private HTTPError error;
	/**
	 * 返回信息
	 */
	private Response response;
	/**
	 * 返回头部信息
	 */
	private Headers headers;
	/**
	 * 返回Body信息
	 */
	private byte[] body;
	/**
	 * 请求成功信息
	 */
	private boolean successful = false;
	/**
	 * 自定义转换信息
	 */
	private HttpConversion httpConversion;
	/**
	 * 实现接口返回类型信息
	 */
	private Type type;
	/**
	 * 原始Body信息
	 */
	private retrofit2.Response<byte[]> resp;

	@Override
	public HTTPError getError() {
		return this.error;
	}

	@Override
	public byte[] getBodyBytes() {
		return this.body;
	}

	@Override
	public String getBodyString() {
		try {
			return new String(this.body,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getHeadersKey(String key) {
		return this.headers.get(key);
	}

	@Override
	public boolean isSuccessful() {
		return this.successful;
	}

	/**
	 * 链式调用
	 * <p>使用 {@code getModel()} 来完成相关功能</p>
	 * @param httpResult
	 * @return
	 */
	@Deprecated
	@Override
	public <E> E isChainModeCall(HttpResult<E, Object> httpResult,E jsonResult) {
		if(isSuccessful()){
			try {
				return httpResult.run(httpConversion.getModel(resp,type));
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return jsonResult;
	}

	/**
	 * 转换结果 {@link com.plugin.httprequest.conversion.HttpReturnConversion}
	 * <p><b>在一定的情况下，model转换失败会直接抛出错误</b></p>
	 * @return
	 */
	@Override
	public Object getModel() {
		return httpConversion.getModel(resp,type);
	}

	/**
	 * 转换结果 {@link HttpReturnConversion}
	 *
	 * @param httpResult
	 * @param jsonResult
	 * @return
	 */
	@Override
	public <E,T> E getModel(HttpResult<E, T> httpResult, E jsonResult) {
		if(isSuccessful()){
			try {
				return httpResult.run((T) httpConversion.getModel(resp,type));
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return jsonResult;
	}

	public void setConversion(HttpConversion httpConversion, retrofit2.Response<byte[]> resp, Type type){
		this.httpConversion = httpConversion;
		this.resp = resp;
		this.type = type;
	}

	public HTTPReaponseModel setError(HTTPError error) {
		this.error = error;
		return this;
	}

	public HTTPReaponseModel setResponse(Response response) {
		this.response = response;
		return this;
	}

	public HTTPReaponseModel setHeaders(Headers headers) {
		this.headers = headers;
		return this;
	}

	public HTTPReaponseModel setBody(byte[] body) {
		this.body = body;
		return this;
	}

	public HTTPReaponseModel setSuccessful(boolean successful) {
		this.successful = successful;
		return this;
	}

}
