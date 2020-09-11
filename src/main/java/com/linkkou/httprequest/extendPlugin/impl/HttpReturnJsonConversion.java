package com.linkkou.httprequest.extendPlugin.impl;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.linkkou.httprequest.extendPlugin.HttpConversion;
import com.linkkou.httprequest.serialization.Json;
import retrofit2.Response;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * 自定义HTTP返回值转换
 *
 * @author LK
 * @version 1.0
 * @data 2017-12-13 19:04
 */
public class HttpReturnJsonConversion implements HttpConversion {

    /**
     * 返回转换类型
     *
     * @return
     */
    @Override
    public String getType() {
        return "GSON";
    }

    /**
     * 转换获取数据转换为Model
     *
     * @param response 数据流
     * @param type     对象类型
     * @return
     */
    @Override
    public Object getModel(Response<byte[]> response, Type type) throws Exception {
        if (type != null) {
            try {
                String returnbody = new String(response.body(), "utf-8");
                return Json.gson.fromJson(returnbody, type);
            } catch (Exception e) {
                type = null;
                e.printStackTrace();
            }
        }
        return Optional.of(type);
    }

    /**
     * body 注解输入对象进行转换
     *
     * @param writer byte输出流
     * @param o      对象
     * @param type   对象类型
     */
    @Override
    public void bodyWriter(Writer writer, Object o, Type type) {
        try {
            if (this.bodyWriterString(writer, o, type)) {
                TypeAdapter adapter = Json.gson.getAdapter(TypeToken.get(type));
                JsonWriter jsonWriter = Json.gson.newJsonWriter(writer);
                adapter.write(jsonWriter, o);
                jsonWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}