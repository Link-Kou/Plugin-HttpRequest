package com.api;

import com.model.ResFixedTokenServes;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.http.*;
import com.plugin.httprequest.HTTPRequest;
import com.plugin.httprequest.HTTPResponse;
import com.plugin.httprequest.retrofitesfactory.converter.cookie.HeadCookies;
import com.plugin.httprequest.extendAnnotations.cookie.Cookie;
import com.plugin.httprequest.extendAnnotations.cookie.Cookies;

/**
 * @author LK
 * @date 2018-04-02 20:33
 */
@HTTPRequest(value = @Value("${djtms.url}"))
public interface tmsfindallprovince {

	@POST("findallprovince.do?id=13213")
	@Cookies({@Cookie(key = "ads",value = "asd")})
	HTTPResponse<ResFixedTokenServes> exchangetoken(@Header("Cookie") HeadCookies s,
	                                                @Header("Cookie") HeadCookies s2,
	                                                @Query("userid") String b);

	@POST("findallprovince.do?id=13213")
	@Cookies({@Cookie(key = "ads",value = "asd")})
	HTTPResponse<ResFixedTokenServes> exchangetoken2(@Body String s);
}
