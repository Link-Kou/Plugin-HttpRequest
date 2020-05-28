package com.Spring;

import com.Spring.api.*;
import com.linkkou.httprequest.HTTPResponse;
import com.linkkou.httprequest.file.FileProgressRequestBody;
import com.linkkou.httprequest.file.MultipartFlieBuild;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;

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
    private ApiMsgPushServer msgPushServer;

    @Autowired
    private OutUser outUserServer;

    @Autowired
    private DownloadFile downloadFile;

    @Autowired
    private ApiCookie apiCookie;

    @Autowired
    private ApiBody apiBody;


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
        final HTTPResponse<ReqOutUser> reqOutUserHTTPResponse = downloadFile.downloadFile("http://pic1.nipic.com/2008-08-14/2008814183939909_2.jpg");

        reqOutUserHTTPResponse.getBodyBytes();
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
