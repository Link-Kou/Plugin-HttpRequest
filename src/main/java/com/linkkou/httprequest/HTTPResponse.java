package com.linkkou.httprequest;

import com.linkkou.httprequest.extendPlugin.HttpConversion;
import com.linkkou.httprequest.extendPlugin.impl.HttpReturnJsonConversion;

/**
 * Created by LK .
 * 公开的以接口形式的返回对象 {@link HTTPReaponseModel}
 *
 * @author LK
 * @version 1.0
 * @data 2017-05-20 12:14
 */
public interface HTTPResponse<T> {

    /**
     * 错误结果
     * 当 {@link #isSuccessful} 为false的时候获取到异常原因
     *
     * @return HTTPError
     */
    HTTPError getError();

    /**
     * body byte
     *
     * @return byte[]
     */
    byte[] getBodyBytes();

    /**
     * body String
     *
     * @return String
     */
    String getBodyString();

    /**
     * 头部信息
     *
     * @param key
     * @return String
     */
    String getHeadersKey(String key);

    /**
     * 执行成功状态，主要是本地发起请求是否成功执行
     * <p>是否连接、读取、写入超时</p>
     * <p>是否IO异常</p>
     * <p>是否其他未知错误</p>
     * <p>不能用于检测HTTP请求成功后,但是远程代码返回的异常</p>
     *
     * @return true 无异常 false 发生异常
     */
    boolean isSuccessful();

    /**
     * 转换结果 {@link HttpConversion}
     * 在一定的情况下，model转换失败会返回null
     *
     * @return T 转换成功返回对象 转换失败返回 null
     */
    T getModel();

    /**
     * 转换结果 {@link HttpConversion}
     * 在一定的情况下，model转换失败会返回null
     *
     * @return T 转换成功返回对象 转换失败返回 null
     */
    T getModel(String name);


}
