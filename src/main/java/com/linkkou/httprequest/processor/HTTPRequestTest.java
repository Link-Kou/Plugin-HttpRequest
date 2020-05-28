package com.linkkou.httprequest.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LK
 * @date 2018-05-31 10:50
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface HTTPRequestTest {

    String value();

}
