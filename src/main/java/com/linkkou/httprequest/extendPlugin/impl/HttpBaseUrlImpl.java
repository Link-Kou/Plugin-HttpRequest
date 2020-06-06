package com.linkkou.httprequest.extendPlugin.impl;

import com.linkkou.httprequest.HTTPRequest;
import org.springframework.beans.factory.annotation.Value;
import com.linkkou.httprequest.extendPlugin.HttpBaseUrl;
import com.linkkou.httprequest.spring.HttpProperties;

import java.util.regex.Pattern;

/**
 * 实现从配置文件中获取URL
 */
public class HttpBaseUrlImpl implements HttpBaseUrl {

    private com.linkkou.httprequest.HTTPRequest HTTPRequest;

    private static Pattern p = Pattern.compile("[\\$\\{\\}]");

    public HttpBaseUrlImpl(HTTPRequest httprequest) {
        this.HTTPRequest = httprequest;
    }

    @Override
    public String geUrl() {
        Value value = HTTPRequest.value();
        String v = p.matcher(value.value()).replaceAll("");
        return HttpProperties.getCtxProp(v);
    }

    public static String geUrl(String value) {
        String v = p.matcher(value).replaceAll("");
        return HttpProperties.getCtxProp(v);
    }

}
