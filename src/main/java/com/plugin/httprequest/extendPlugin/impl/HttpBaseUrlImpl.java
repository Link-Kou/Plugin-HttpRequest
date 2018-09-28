package com.plugin.httprequest.extendPlugin.impl;

import org.springframework.beans.factory.annotation.Value;
import com.plugin.httprequest.HTTPRequest;
import com.plugin.httprequest.extendPlugin.HttpBaseUrl;
import com.plugin.httprequest.spring.HttpProperties;

import java.util.regex.Pattern;

public class HttpBaseUrlImpl implements HttpBaseUrl {

    private HTTPRequest HTTPRequest;

    private static Pattern p = Pattern.compile("[\\$\\{\\}]");

    public HttpBaseUrlImpl(HTTPRequest httprequest){
        this.HTTPRequest = httprequest;
    }

    @Override
    public String geUrl() {
        Value value = HTTPRequest.value();
        String v = p.matcher(value.value()).replaceAll("");
        return HttpProperties.getCtxProp(v);
    }

}
