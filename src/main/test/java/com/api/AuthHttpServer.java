package com.api;

import com.model.ResFixedTokenServes;
import com.model.ResUserInfoServers;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.http.*;
import com.plugin.httprequest.HTTPRequest;
import com.plugin.httprequest.HTTPResponse;
import com.plugin.httprequest.extendAnnotations.cookie.Cookie;
import com.plugin.httprequest.extendAnnotations.cookie.Cookies;

/**
 * @author LK
 * @date 2018/3/21
 * @description
 */
@HTTPRequest(value = @Value("${djnewauth.url}"))
public interface AuthHttpServer {

	/**
	 * 根据一次性Token转换为固定Token
	 *
	 * @return HTTPResponse<FixedToken>
	 * @date  2018-1-15 13:48:31
	 * @author lk
	 */
	@FormUrlEncoded
	@POST("exchangetoken.do")
	@Cookies({@Cookie(key = "",value = ""),@Cookie(key = "",value = "")})
	HTTPResponse<ResFixedTokenServes> exchangetoken(@Field("onceToken") String onceToken, @Header("Cookie")String s);

	/**
	 * 通过token得到用户信息
	 *
	 * @return HTTPResponse<FixedToken>
	 * @date  2018-1-15 13:48:31
	 * @author lk
	 */
	@FormUrlEncoded
	@POST("getUserByToken.do")
	HTTPResponse<ResUserInfoServers> getUserByToken(@Field("token") String Token);
}
