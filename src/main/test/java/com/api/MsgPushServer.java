package com.api;

import com.model.MsgPushServerParam;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import com.plugin.httprequest.HTTPRequest;
import com.plugin.httprequest.HTTPResponse;

/**
 * 与消息服务对接
 * @author LK
 * @version 1.0
 * @date 2017-12-22 21:11
 */
@HTTPRequest(value = @Value("${message.url}"))
public interface MsgPushServer {

	/**
	 * 发送验证码
	 * @param phone
	 * @param msg
	 * @param appSystem
	 * @return
	 */
	@FormUrlEncoded
	@POST("sendmsgphone.do")
	HTTPResponse<MsgPushServerParam> sendCode(@Field("phone") String phone,
	                                          @Field("msg") String msg,
	                                          @Field("appSystem") String appSystem);

}
