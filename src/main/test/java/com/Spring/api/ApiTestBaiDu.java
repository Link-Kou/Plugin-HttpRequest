package com.Spring.api;

import com.linkkou.httprequest.HTTPRequest;
import com.linkkou.httprequest.HTTPResponse;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 高德API示列
 *
 * @author lk
 * @version 1.0
 * @date 2019/4/17 17:53
 */
@HTTPRequest(value = @Value("${baidu.url}"))
public interface ApiTestBaiDu {


    /**
     * 高德 天气查询
     *
     * @param key        用户在高德地图官网申请web服务API类型KEY
     * @return 天气对象
     */
    @GET("/dd")
    HTTPResponse<String> baidu(@Query("key") String key);


}
