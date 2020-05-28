package com.Spring.api;

import com.linkkou.httprequest.HTTPRequest;
import com.linkkou.httprequest.HTTPResponse;
import com.linkkou.httprequest.extendAnnotations.Convert;
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
@HTTPRequest(value = @Value("${marketing.url}"))
public interface ApiBody {
    /**
     * 获取开团时间
     *
     * @return
     */
    @POST("pricescheme/getopentime.do")
    HTTPResponse<String> marketingGetopentime(@Body ResGetopentime resGetopentime);

    /**
     * 获取开团时间
     *
     * @return
     */
    @POST("pricescheme/getopentime.do")
    //自定义转换器
    @Convert("GSON2")
    HTTPResponse<String> marketingGetopentime2(@Body ResGetopentime resGetopentime);

}
