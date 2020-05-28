package com.linkkou.httprequest.extendAnnotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
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
public @interface Convert {
    String value();
}
