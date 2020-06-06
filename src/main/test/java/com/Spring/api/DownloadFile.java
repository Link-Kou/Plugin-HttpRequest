package com.Spring.api;

import com.linkkou.httprequest.HTTPRequest;
import com.linkkou.httprequest.HTTPResponse;
import com.linkkou.httprequest.extendAnnotations.Log;
import com.linkkou.httprequest.extendAnnotations.Retry;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

/**
 * @author lk
 * @version 1.0
 * @date 2019/4/23 10:26
 */
@HTTPRequest(value = @Value("${downloadfile.url}"), callTimeout = 120)
public interface DownloadFile {

    /**
     * 下载视频
     *
     * @return
     */
    //@Streaming
    @Retry(retryType = @Retry.RetryType(
            CallTime = true
    ))
    @GET
    @Log(false)
    HTTPResponse<ReqOutUser> downloadFile(@Url String fileUrl);

    /**
     * 下载视频
     *
     * @return
     */
    @Streaming
    @GET
    @Log(false)
    Observable<ResponseBody> ObdownloadFile(@Url String fileUrl);

    /**
     * 下载视频
     *
     * @return
     */
    @Streaming
    @GET
    @Log(false)
    Call<ResponseBody> CalldownloadFile(@Url String fileUrl);

}
