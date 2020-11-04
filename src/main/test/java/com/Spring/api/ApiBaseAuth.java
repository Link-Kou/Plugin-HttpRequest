package com.Spring.api;

import com.linkkou.httprequest.HTTPRequest;
import com.linkkou.httprequest.HTTPResponse;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * 高德API示列
 *
 * @author lk
 * @version 1.0
 * @date 2019/4/17 17:53
 */
@HTTPRequest(value = @Value("${RESTAdapter}"))
public interface ApiBaseAuth {


    /**
     * Base Auth接口
     *
     * @param param 随意
     * @return String
     */
    @POST("RESTAdapter/BPC000201/send")
    HTTPResponse<String> get(@Body String param, @Header("Authorization") String name);


}
