package com.plugin.httprequest.conversion;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.plugin.json.Json;
import retrofit2.Response;
import com.plugin.httprequest.extendPlugin.HttpConversion;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
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
public class HttpReturnConversion implements HttpConversion {

    /**
     * GSON json 输出
     */
    //数据转换工具
    private class json extends Json {
        @Override
        protected <T> T fromJson(String json, Type type) {
            return super.fromJson(json, type);
        }

        @Override
        protected  <T> TypeAdapter<T> getAdapter(TypeToken<T> type) {
            return super.getAdapter(type);
        }

        @Override
        protected JsonWriter newJsonWriter(Writer writer) throws IOException {
            return super.newJsonWriter(writer);
        }
    }


    /**
     * 转换获取数据转换为Model
     *
     * @param response 数据流
     * @param type     对象类型
     * @return
     */
    @Override
    public Object getModel(Response<byte[]> response, Type type) {
        if (type != null) {
            try {
                String returnbody = new String(response.body(), "utf-8");
                return new json().fromJson(returnbody, type);
            } catch (Exception e) {
                type = null;
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(type)
                .orElseThrow(() -> new EntityNotFoundException("HTTP HttpReturnConversion Data The Unknown"));
    }

    /**
     * @param writer byte输出流
     * @param o      对象
     * @param type   对象类型
     * @body注解输入对象进行转换
     */
    @Override
    public void bodyWriter(Writer writer, Object o, Type type) {
        try {
            if (type instanceof ParameterizedTypeImpl) { }
            if (type instanceof Class) {
                if (((Class) type).getName().equals("java.lang.String")) {
                    for (char c : String.valueOf(o).toCharArray()) {
                        writer.append(c);
                    }
                    writer.close();
                    return;
                }
            }
            final json json = new json();
            TypeAdapter adapter = json.getAdapter(TypeToken.get(type));
            JsonWriter jsonWriter = json.newJsonWriter(writer);
            adapter.write(jsonWriter, o);
            jsonWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}