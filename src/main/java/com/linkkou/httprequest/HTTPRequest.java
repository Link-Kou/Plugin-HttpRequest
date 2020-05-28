package com.linkkou.httprequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by LK on 2017/5/19.
 */

/**
 * Created by LK .
 * 注解扫描实现需要实现代理的接口，以此注解为查找依据
 *
 * @author LK
 * @version 1.0
 * @data 2017-05-19 12:14
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component // 标明可被 Spring 扫描
public @interface HTTPRequest {

    /**
     * 是否启用重试
     */
    public @interface Retry {
        /**
         * 是否启用重试
         *
         * @return
         */
        boolean retryFailure() default false;

        /**
         * 重试次数
         * 重试次数为1，重试后默认请求加上重试次数一共会发送2次请求
         *
         * @return
         */
        int retryNumber() default 0;

        /**
         * 重定向url
         *
         * @return
         */
        Value retryUrl() default @Value("");
    }


    /**
     * 配置SSL
     */
    public @interface SSL {

        /**
         * SSL配置属性
         *
         * @author peida
         */
        public enum SSLConfig {
            /**
             * 默认
             */
            DEFAULT,
            /**
             * 取消校验SSL
             */
            NOTSSL,
            /**
             * 自己定义SSL的p12文件进行请求
             */
            OWNSSL
        }

        /**
         * 安全请求配置
         *
         * @return
         */
        SSLConfig SSL() default SSLConfig.DEFAULT;

        /**
         * 双向安全证书
         * 从资源文件夹中读取
         *
         * @return
         */
        String SSLfile() default "";

        /**
         * 双向安全证书密码
         *
         * @return
         */
        String SSLPassword() default "";
    }

    /**
     * 配置URL值
     *
     * @return
     */
    Value value();

    /**
     * 写入超时
     *
     * @return
     */
    long writeTimeout() default 15;

    /**
     * 读取超时
     *
     * @return
     */
    long readTimeout() default 15;

    /**
     * 链接超时
     *
     * @return
     */
    long connectTimeout() default 15;

    /**
     * 是否启用重试
     *
     * @return
     */
    Retry retry() default @Retry;

    /**
     * 配置SSL
     *
     * @return
     */
    SSL ssl() default @SSL;

}
