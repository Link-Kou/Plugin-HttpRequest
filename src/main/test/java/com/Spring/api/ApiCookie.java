package com.Spring.api;

import com.linkkou.httprequest.HTTPRequest;
import com.linkkou.httprequest.HTTPResponse;
import com.linkkou.httprequest.extendAnnotations.cookie.Cookie;
import com.linkkou.httprequest.extendAnnotations.cookie.Cookies;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 高德API示列
 *
 * @author lk
 * @version 1.0
 * @date 2019/4/17 17:53
 */
@HTTPRequest(value = @Value("${amap.url}"))
public interface ApiCookie {

    /**
     * 获取开团时间
     *
     * @return
     */
    @POST("pricescheme/getopentime.do")
    @Cookies({@Cookie(key = "asd", value = "asda"), @Cookie(key = "asd2", value = "asda3")})
    HTTPResponse<String> marketingGetopentime(@Body ResGetopentime resGetopentime);

}
