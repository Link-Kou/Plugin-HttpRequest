package com.Spring.api;

import com.linkkou.httprequest.HTTPRequest;
import com.linkkou.httprequest.HTTPResponse;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.http.*;

/**
 * @author lk
 * @version 1.0
 * @date 2019/4/23 10:26
 */
@HTTPRequest(value = @Value("${outuser.url}"))
public interface DownloadFile {

    /**
     * 下载视频
     *
     * @return
     */
    @Streaming
    @GET
    HTTPResponse<ReqOutUser> downloadFile(@Url String fileUrl);

}
