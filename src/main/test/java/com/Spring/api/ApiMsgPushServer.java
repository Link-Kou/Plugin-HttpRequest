package com.Spring.api;

import com.linkkou.httprequest.HTTPRequest;
import com.linkkou.httprequest.HTTPResponse;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 与消息服务对接
 *
 * @author LK
 * @version 1.0
 * @date 2017-12-22 21:11
 */
@HTTPRequest(
        value = @Value("${message.url}")
)
public interface ApiMsgPushServer {

    /**
     * 发送验证码
     *
     * @param phone
     * @param msg
     * @param appSystem
     * @return
     */
    @FormUrlEncoded
    @POST("sendmsgphone.do")
    HTTPResponse<ReqMsgPushServerParam> sendCode(@Field("phone") String phone,
                                                 @Field("msg") String msg,
                                                 @Field("appSystem") String appSystem);

}
