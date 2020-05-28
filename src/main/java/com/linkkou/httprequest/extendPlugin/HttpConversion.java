package com.linkkou.httprequest.extendPlugin;

import retrofit2.Response;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * Created by LK
 * 用于自定义解析返回对象
 *
 * @author LK
 * @version 1.0
 * @data 2017-12-12 12:13
 */
public interface HttpConversion {

    /**
     * 返回转换类型
     *
     * @return
     */
    String getType();

    /**
     * 进行http返回值对象转换
     *
     * @param PR   原始数据
     * @param type 返回对象类
     * @return
     */
    Object getModel(Response<byte[]> PR, Type type) throws Exception;

    /**
     * 对注解@body 进行个性化转换
     *
     * @param writer  输出
     * @param value   对象
     * @param adapter 类型
     */
    void bodyWriter(Writer writer, Object value, Type adapter);

    /**
     * 对注解@body 对String进行默认转换
     * @param writer
     * @param value
     * @param adapter
     */
    default void bodyWriterString(Writer writer, Object value, Type adapter) throws IOException {
        if (adapter instanceof Class) {
            if (((Class) adapter).getName().equals("java.lang.String")) {
                for (char c : String.valueOf(value).toCharArray()) {
                    writer.append(c);
                }
                writer.close();
                return;
            }
        }
    }
}
