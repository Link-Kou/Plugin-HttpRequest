package com.linkkou.httprequest.ssl;

import java.io.InputStream;

/**
 * 自定义加载SSL文件
 * @author lk
 * @version 1.0
 * @date 2020/11/4 10:33
 */
public interface SSLLoadFile {

    /**
     * 文件流程
     * @return InputStream
     */
    InputStream getFile();

    /**
     * 密码
     * @return
     */
    String getPassword();
}
