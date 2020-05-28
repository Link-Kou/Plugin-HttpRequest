package com.linkkou.httprequest;

import com.linkkou.httprequest.retrofitesfactory.HTTPReaponseCallAdapterFactory;
import com.linkkou.httprequest.extendPlugin.impl.HttpReturnJsonConversion;
import okhttp3.Headers;
import okhttp3.Response;
import com.linkkou.httprequest.extendPlugin.HttpConversion;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 非公开返回实体对象
 * 被{@link HTTPReaponseCallAdapterFactory#get}具体使用
 *
 * @author LK
 * @version 1.0
 * @data 2017-05-20 12:14
 */
public class HTTPReaponseModel implements HTTPResponse<Object> {

    /**
     * 错误信息
     */
    private HTTPError error;
    /**
     * 返回信息
     */
    private Response response;
    /**
     * 返回头部信息
     */
    private Headers headers;
    /**
     * 返回Body信息
     */
    private byte[] body;
    /**
     * 请求成功信息
     */
    private boolean successful = false;
    /**
     * 自定义转换信息
     */
    private List<HttpConversion> httpConversions;
    /**
     * 实现接口返回类型信息
     */
    private Type type;
    /**
     * 原始Body信息
     */
    private retrofit2.Response<byte[]> resp;

    @Override
    public HTTPError getError() {
        return this.error;
    }

    @Override
    public byte[] getBodyBytes() {
        return this.body;
    }

    @Override
    public String getBodyString() {
        try {
            if (this.body != null) {
                return new String(this.body, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getHeadersKey(String key) {
        return this.headers.get(key);
    }

    @Override
    public boolean isSuccessful() {
        return this.successful;
    }

    /**
     * 转换结果 {@link HttpReturnJsonConversion}
     * <p><b>在一定的情况下，model转换会失败,失败后返回null</b></p>
     *
     * @return
     */
    @Override
    public Object getModel() {
        return getModel("GSON");
    }

    /**
     * 转换结果 {@link HttpReturnJsonConversion}
     * <p><b>在一定的情况下，model转换会失败,失败后返回null</b></p>
     *
     * @return
     */
    @Override
    public Object getModel(String name) {
        try {
            if (httpConversions != null && httpConversions.size() > 0) {
                for (HttpConversion httpConversion : httpConversions) {
                    if (httpConversion.getType().equals(name)) {
                        return httpConversion.getModel(resp, type);
                    }
                }
                return httpConversions.get(0).getModel(resp, type);
            } else {
                return new HttpReturnJsonConversion().getModel(resp, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setConversion(List<HttpConversion> httpConversion, retrofit2.Response<byte[]> resp, Type type) {
        this.httpConversions = httpConversion;
        this.resp = resp;
        this.type = type;
    }

    public HTTPReaponseModel setError(HTTPError error) {
        this.error = error;
        return this;
    }

    public HTTPReaponseModel setResponse(Response response) {
        this.response = response;
        return this;
    }

    public HTTPReaponseModel setHeaders(Headers headers) {
        this.headers = headers;
        return this;
    }

    public HTTPReaponseModel setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public HTTPReaponseModel setSuccessful(boolean successful) {
        this.successful = successful;
        return this;
    }

}
