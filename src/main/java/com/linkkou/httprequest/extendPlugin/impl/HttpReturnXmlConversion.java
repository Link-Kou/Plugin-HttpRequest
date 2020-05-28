package com.linkkou.httprequest.extendPlugin.impl;

import com.linkkou.httprequest.extendPlugin.HttpConversion;
import com.linkkou.httprequest.serialization.Xml;
import retrofit2.Response;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.persistence.EntityNotFoundException;
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
public class HttpReturnXmlConversion implements HttpConversion {

    /**
     * 返回转换类型
     *
     * @return
     */
    @Override
    public String getType() {
        return "XML";
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
                Xml.simple.read(type, returnbody);
            } catch (Exception e) {
                type = null;
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(type)
                .orElseThrow(() -> new EntityNotFoundException("HTTP HttpReturnJsonConversion Data The Unknown"));
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
            if (type instanceof ParameterizedTypeImpl) {
            }
            this.bodyWriterString(writer, o, type);
            Xml.simple.write(o, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}