package com.plugin.httprequest.retrofitesfactory;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;


/**
 * Created by LK .
 * 返回整个http请求结果以Byte
 * @author LK
 * @version 1.0
 * @data 2017-06-28 14:12
 */
public class HTTPResponseConverter implements Converter<ResponseBody, byte[]> {

	public static final HTTPResponseConverter INSTANCE = new HTTPResponseConverter();

	@Override
	public byte[] convert(ResponseBody responseBody) throws IOException {
		return responseBody.bytes();
	}
}
