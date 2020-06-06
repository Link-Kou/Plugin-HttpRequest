package com.linkkou.httprequest.extendAnnotations;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用于@Body说明转换类型
 *
 * @author lk
 * @version 1.0
 * @date 2019/4/28 17:00
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface Retry {

    /**
     * 重试
     */
    @interface RetryType {
        /**
         * 连接超时
         * @return boolean
         */
        boolean SocketTimeout() default false;
        /**
         * 运行超时
         * @return boolean
         */
        boolean RunTime() default false;
        /**
         * 执行超时
         * @return boolean
         */
        boolean CallTime() default false;
        /**
         * 未知异常
         * @return boolean
         */
        boolean Unknown() default false;
    }



    /**
     * 重试次数
     * 重试次数为1，重试后默认请求加上重试次数一共会发送2次请求
     *
     * @return
     */
    int retryNumber() default 1;

    /**
     * 重试类型
     * @return
     */
    RetryType retryType() default @RetryType;


}
