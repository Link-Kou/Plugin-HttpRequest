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
// 标明可被 Spring 扫描
@Component
public @interface HTTPRequest {

    /**
     * 配置SSL
     */
    @interface SSL {

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
            OWNSSL,
            /**
             * 自己定义SSL的加载路径
             */
            OWNClASSSSL
        }

        /**
         * 安全请求配置
         *
         * @return
         */
        SSLConfig SSL() default SSLConfig.DEFAULT;

        /**
         * 执行相关类型,获取到文件流程
         * @return Class
         */
        Class<?> SSLLoad() default String.class;

        /**
         * 双向安全证书
         * 从执行路径中读取,路径为绝对路径
         * @return String
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
     * 写入超时 秒
     *
     * @return
     */
    long writeTimeout() default 8;

    /**
     * 读取超时 秒
     *
     * @return
     */
    long readTimeout() default 8;

    /**
     * 链接超时 秒
     *
     * @return
     */
    long connectTimeout() default 8;

    /**
     * 请求过程超时 秒
     *
     * @return
     */
    long callTimeout() default 8;

    /**
     * 配置SSL
     *
     * @return
     */
    SSL ssl() default @SSL;

}
