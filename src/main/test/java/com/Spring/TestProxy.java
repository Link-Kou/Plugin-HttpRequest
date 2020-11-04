package com.Spring;

import com.Spring.api.*;
import com.linkkou.httprequest.HTTPResponse;
import com.linkkou.httprequest.file.FileProgressRequestBody;
import com.linkkou.httprequest.file.MultipartFlieBuild;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author lk
 * @version 1.0
 * @date 2019/4/17 17:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/config/spring/spring-mvc.xml"})
public class TestProxy {


    private static Logger logger = LoggerFactory.getLogger(TestProxy.class);

    @Autowired
    private ApiAmap amap;

    @Autowired
    private ApiTestBaiDu baiDu;

    @Autowired
    private ApiMsgPushServer msgPushServer;

    @Autowired
    private OutUser outUserServer;

    @Autowired
    private DownloadFile downloadFile;

    @Autowired
    private ApiCookie apiCookie;

    @Autowired
    private ApiBody apiBody;

    @Autowired
    private ApiBaseAuth apiBaseAuth;

    /**
     * Base 加密
     */
    @Test
    public void GetApiBaseAuth() {
        /**
         * 请求
         */
        final HTTPResponse<String> basic = apiBaseAuth.get("{\"reqJSON\":\"{\\\"item\\\":{\\\"message\\\": \\\"7788\\\"}}\"}", "Basic UElVU0VSOkluaXQxMjM0");
        if (basic.isSuccessful()) {
            logger.debug(String.format(" apiBaseAuth --> %s", basic.getBodyString()));
        }
    }

    /**
     * 高德地图-天气接口
     * Get请求示列
     * 请求传递  Url
     * 请求传参  ?key=%s&....
     */
    @Test
    public void GetWeather() {
        /**
         * 请求
         */
        final HTTPResponse<RepWeather> weather = amap.weather("7bb4c8a8ff0745c44f3bf0a5b35b389b", "110000", null, null);
        if (weather.isSuccessful()) {
            logger.debug(String.format(" weather --> %s", weather.getBodyString()));
        }
    }

    /**
     * 百度
     * Get请求示列
     * 请求传递  Url
     * 请求传参  ?key=%s&....
     */
    @Test
    public void GetBaidu() {
        /**
         * 请求
         */
        final HTTPResponse<String> weather = baiDu.baidu("7bb4c8a8ff0745c44f3bf0a5b35b389b");
        if (weather.isSuccessful()) {
            logger.debug(String.format(" weather --> %s", weather.getBodyString()));
        }
    }

    /**
     * 原生版本 - 高德地图-天气接口
     * Get请求示列
     * 请求传递  Url
     * 请求传参  ?key=%s&....
     */
    @Test
    public void GetPrimaryWeather() throws IOException {
        /**
         * 请求
         */
        final Call<ResponseBody> responseBodyCall = amap.weatherPrimary("7bb4c8a8ff0745c44f3bf0a5b35b389b", "110000", null, null);
        final Response<ResponseBody> execute = responseBodyCall.execute();
        if (execute.isSuccessful()) {
            final ResponseBody body = execute.body();
            String returnbody = new String(body.bytes(), "utf-8");
        }
    }

    /**
     * RX版本 - 高德地图-天气接口
     * Get请求示列
     * 请求传递  Url
     * 请求传参  ?key=%s&....
     */
    @Test
    public void GetRXPrimaryWeather() throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        /**
         * 请求
         */
        final Observable<ResponseBody> stringObservable = amap.ObWeather("7bb4c8a8ff0745c44f3bf0a5b35b389b", "110000", null, null);
        stringObservable.subscribeOn(Schedulers.io()).subscribe(x -> {
            Thread.sleep(3000);
            System.out.println("->>>>>" + x.string());
        }, x -> {

        });
        long endTime = System.currentTimeMillis();
        System.out.println("当前程序耗时：" + (endTime - startTime) + "ms");
        new CountDownLatch(1).await(3, TimeUnit.MINUTES);
        /*if (execute.isSuccessful()) {
            final ResponseBody body = execute.body();
            String returnbody = new String(body.bytes(), "utf-8");
        }*/
    }

    /**
     * 高德地图-天气接口
     * Get请求示列
     * 请求传递  Url
     * 请求传参  ?key=%s&....
     */
    @Test
    public void GetWeather2() {
        /**
         * 请求
         */
        final HTTPResponse<RepWeather> weather = amap.weather("7bb4c8a8ff0745c44f3bf0a5b35b389b", "110000", null, null);
        if (weather.isSuccessful()) {
            final RepWeather model = weather.getModel();
            if (weather.isSuccessful()) {
                final Optional<RepWeather> model1 = Optional.ofNullable(model);
                if (model1.isPresent()) {
                    model1.map(x -> x.getStatus()).orElse(0);
                } else {
                    logger.debug(String.format(" weather2 --转换失败--> %s", weather.getBodyString()));
                }
                logger.debug(String.format(" weather2 --> %s", weather.getBodyString()));
            }
        }
    }

    /**
     * 发送短信
     * Post请求示列
     * 请求传递  Form
     * 请求传参  Phone=%s&....
     */
    @Test
    public void PostSendCode() {
        /**
         * 请求
         */
        final HTTPResponse<ReqMsgPushServerParam> sendCode = msgPushServer.sendCode("15858832888", "测试", "ios");
        if (sendCode.isSuccessful()) {
            logger.debug(String.format(" sendCode --> %s", sendCode.getBodyString()));
        }
    }


    /**
     * 上传图片-无进度监听
     */
    @Test
    public void PostOutUser() {
        final MultipartBody.Part part = MultipartFlieBuild.get(new File("/Users/lk/Downloads/zhitaoicon.png"));
        final HTTPResponse<ReqOutUser> outUser = outUserServer.fileUpload(part);
        if (outUser.isSuccessful()) {
            final ReqOutUser model = outUser.getModel();
            final Optional<ReqOutUser> model1 = Optional.ofNullable(model);
            if (model1.isPresent()) {
                final ReqOutUser reqOutUser = model1.get();
                reqOutUser.getData();
            }
        }
    }

    /**
     * 上传图片-进度监听
     */
    @Test
    public void PostOutUserProgressListener() {
        final MultipartBody.Part part = MultipartFlieBuild.get(new File("/Users/lk/Downloads/zhitaoicon.png"),
                new FileProgressRequestBody.ProgressListener() {
                    /**
                     * 进度返回
                     *
                     * @param progressSize 已上传大小
                     * @param fileSize     文件大小
                     */
                    @Override
                    public void transferred(long progressSize, long fileSize) {
                        logger.debug(String.format(" progressListener --> progressSize:%s fileSize:%s", progressSize, fileSize));
                    }
                });
        final HTTPResponse<ReqOutUser> outUser = outUserServer.fileUpload(part);
    }

    /**
     * 多文件上传图片-无法监听上传进度
     */
    @Test
    public void PostMuchOutUser() {
        HashMap<String, RequestBody> Flies = new HashMap<String, RequestBody>();
        Flies.put("file", MultipartFlieBuild.getRequestBody(new File("/Users/lk/Downloads/zhitaoicon.png")));
        final HTTPResponse<ReqOutUser> outUser = outUserServer.fileUpload2(Flies);
    }

    /**
     * 文件下载图片
     */
    @Test
    public void GetDownloadFile() {
        /**
         * 同步阻塞，服务端目前无场景需要实现下载功能
         */
        final HTTPResponse<ReqOutUser> reqOutUserHTTPResponse = downloadFile.downloadFile("100MB-atlanta.bin");
        reqOutUserHTTPResponse.getBodyBytes();
    }


    /**
     * 文件下载图片
     */
    @Test
    public void GetObDownloadFile() throws InterruptedException {
        /**
         * 同步阻塞，服务端目前无场景需要实现下载功能
         */
        final Observable<ResponseBody> responseBodyObservable = downloadFile.ObdownloadFile("100MB-atlanta.bin");
        responseBodyObservable.subscribeOn(Schedulers.io())
                .flatMap(x -> {
                    Disk.writeResponseBodyToDisk(x);
                    return Observable.just(true);
                })
                .subscribe(x -> {
                    System.out.println("asdasd");
                });
        new CountDownLatch(1).await(3, TimeUnit.MINUTES);
    }

    /**
     * 文件下载图片
     */
    @Test
    public void GetCallDownloadFile() throws InterruptedException {
        /**
         * 同步阻塞，服务端目前无场景需要实现下载功能
         */
        final Call<ResponseBody> responseBodyCall = downloadFile.CalldownloadFile("100MB-atlanta.bin");
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    logger.info("server contacted and has file");
                    Disk.writeResponseBodyToDisk(response.body());
                } else {
                    logger.info("server contacted and has file");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                logger.info("Error");
            }
        });
        new CountDownLatch(1).await(3, TimeUnit.MINUTES);
    }


    /**
     * 文件下载图片
     */
    @Test
    public void GetCookie() {


    }

    /**
     * 获取总开团时间
     */
    @Test
    public void ApiBody() {
      /*  final HTTPResponse<String> stringHTTPResponse = apiBody.marketingGetopentime(
                new ResGetopentime()
                        .setBusiness("2")
                        .setDevTokenId("123456")
                        .setPlatform("3")
                        .setSystem("4")
                        .setVersion("123")
        );
        stringHTTPResponse.getBodyString();*/

        final HTTPResponse<String> stringHTTPResponse2 = apiBody.marketingGetopentime2(
                new ResGetopentime()
                        .setBusiness("2")
                        .setDevTokenId("123456")
                        .setPlatform("3")
                        .setSystem("4")
                        .setVersion("123")
        );
        stringHTTPResponse2.getBodyString();
    }

}
