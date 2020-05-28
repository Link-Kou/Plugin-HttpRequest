package com.Spring.api;

import com.linkkou.httprequest.HTTPRequest;
import com.linkkou.httprequest.HTTPResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.http.*;

import java.util.Map;

/**
 * @author lk
 * @version 1.0
 * @date 2019/4/23 10:26
 */
@HTTPRequest(value = @Value("${outuser.url}"))
public interface OutUser {


    /**
     * 用户文件上传
     *
     * @return 天气对象
     */
    @Multipart
    @POST("file/uploadFile/stream.do")
    HTTPResponse<ReqOutUser> fileUpload(@Part MultipartBody.Part part);

    /**
     * 用户文件上传
     *
     * @return 天气对象
     */
    @Multipart
    @POST("file/uploadFile/stream.do")
    HTTPResponse<ReqOutUser> fileUpload2(@PartMap Map<String, RequestBody> partMap);


}
