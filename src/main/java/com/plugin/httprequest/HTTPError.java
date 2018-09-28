package com.plugin.httprequest;


/**
 * Created by LK .
 * 错误异常说明
 * @author LK
 * @version 1.0
 * @data 2017-06-25 12:14
 */
public enum HTTPError {
	/**
	 * 请求成功
	 */
	OK,
	/**
	 * 超时
	 */
	SocketTimeout,
	/***
	 * 运行超时
	 */
	Runtime,
	/**
	 * 输入输出超时
	 */
	IOException
}
