package com.plugin.httprequest.extendAnnotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author lk
 * @date 2018/9/20 11:20
 */
@Documented
@Target({ METHOD})
@Retention(RUNTIME)
public @interface GetBody {
}
