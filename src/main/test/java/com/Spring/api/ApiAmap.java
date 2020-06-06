package com.Spring.api;

import com.linkkou.httprequest.HTTPRequest;
import com.linkkou.httprequest.HTTPResponse;
import io.reactivex.rxjava3.core.Observable;
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
@HTTPRequest(value = @Value("${amap.url}"))
public interface ApiAmap {


    /**
     * 高德 天气查询
     *
     * @param key        用户在高德地图官网申请web服务API类型KEY
     * @param city       输入城市的adcode，adcode信息可参考城市编码表
     * @param extensions 可选值：base/all base:返回实况天气 all:返回预报天气 可选性输入null
     * @param output     可选值：JSON,XML 可选性输入null
     * @return 天气对象
     */
    @GET("weather/weatherInfo")
    HTTPResponse<RepWeather> weather(@Query("key") String key,
                                     @Query("city") String city,
                                     @Query("extensions") String extensions,
                                     @Query("output") String output);


    /**
     * 高德 天气查询
     *
     * @param key        用户在高德地图官网申请web服务API类型KEY
     * @param city       输入城市的adcode，adcode信息可参考城市编码表
     * @param extensions 可选值：base/all base:返回实况天气 all:返回预报天气 可选性输入null
     * @param output     可选值：JSON,XML 可选性输入null
     * @return 天气对象
     */
    @GET("weather/weatherInfo")
    Observable<ResponseBody> ObWeather(@Query("key") String key,
                               @Query("city") String city,
                               @Query("extensions") String extensions,
                               @Query("output") String output);


    /**
     * 高德 天气查询
     *
     * @param key        用户在高德地图官网申请web服务API类型KEY
     * @param city       输入城市的adcode，adcode信息可参考城市编码表
     * @param extensions 可选值：base/all base:返回实况天气 all:返回预报天气 可选性输入null
     * @param output     可选值：JSON,XML 可选性输入null
     * @return 天气对象
     */
    @GET("weather/weatherInfo")
    Call<ResponseBody> weatherPrimary(@Query("key") String key,
                                      @Query("city") String city,
                                      @Query("extensions") String extensions,
                                      @Query("output") String output);

}
