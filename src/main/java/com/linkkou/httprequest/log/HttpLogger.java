package com.linkkou.httprequest.log;


import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by LK .
 * 日志拦截使用，需要配合logf4j2
 *
 * @author LK
 * @version 1.0
 * @data 2017-12-12 12:14
 */
public class HttpLogger implements HttpLoggingInterceptor.Logger {

    private static Logger logger = LoggerFactory.getLogger(HttpLogger.class);

    private StringBuilder mMessage = new StringBuilder();

    @Override
    public void log(String message) {
        /**
         * 将逐条输出改为一次性输出
         */
        if (!"".equals(message)) {
            mMessage.append(System.getProperty("line.separator"));
        }
        mMessage.append(message);
        // 响应结束，打印整条日志
        if (message.startsWith("<-- END") || message.startsWith("<-- HTTP FAILED")) {
            logger.debug(mMessage.toString());
        }
    }
}
