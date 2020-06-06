package com.linkkou.httprequest.serialization;

import com.google.gson.*;

/**
 * 此JSON工具只用于本包内
 *
 * @author LK
 * @version 1.0
 * @date 2017-12-28 15:29
 */
public class Json {

    public static Gson gson = new GsonBuilder()
            //时间格式化
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            //去除Null值
            .serializeNulls()
            //禁用html转义
            .disableHtmlEscaping()
            .create();


}