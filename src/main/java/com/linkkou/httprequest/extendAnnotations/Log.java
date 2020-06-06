package com.linkkou.httprequest.extendAnnotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 是否开启日志
 * 文件流的不建议开启日志
 *
 * @author lk
 * @version 1.0
 * @date 2020/6/6 10:42
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface Log {
    boolean value() default true;
}
