package com.Spring.api;

import com.linkkou.httprequest.HTTPRequest;
import org.springframework.beans.factory.annotation.Value;

/**
 * 高德API示列
 *
 * @author lk
 * @version 1.0
 * @date 2019/4/17 17:53
 */
@HTTPRequest(value = @Value("${amap.url}"))
public interface ApiCookie {



}
