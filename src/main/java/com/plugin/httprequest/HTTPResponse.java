package com.plugin.httprequest;

/**
 * Created by LK .
 * 公开的以接口形式的返回对象 {@link HTTPReaponseModel}
 * @author LK
 * @version 1.0
 * @data 2017-05-20 12:14
 */
public interface HTTPResponse<T> {

	/**
	 * 错误结果
	 * @return
	 */
	HTTPError getError();

	/**
	 * body byte
	 * @return
	 */
	byte[] getBodyBytes();

	/**
	 * body String
	 * @return
	 */
	String getBodyString();

	/**
	 * 头部信息
	 * @param key
	 * @return
	 */
	String getHeadersKey(String key);

	/**
	 * 成功状态
	 * @return
	 */
	boolean isSuccessful();

	/**
	 * 链式调用 {@link HttpResult}
	 * @param httpResult
	 * @param <E>
	 * @return
	 */
	@Deprecated
	<E> E isChainModeCall(HttpResult<E, T> httpResult,E jsonResult);

	/**
	 * 转换结果 {@link com.plugin.httprequest.conversion.HttpReturnConversion}
	 * 在一定的情况下，model转换失败会直接抛出错误
	 * @return
	 */
	T getModel();


	/**
	 * 转换结果 {@link com.plugin.httprequest.conversion.HttpReturnConversion}
	 * @return
	 */
	<E,T> E getModel(HttpResult<E, T> httpResult,E jsonResult);

}
