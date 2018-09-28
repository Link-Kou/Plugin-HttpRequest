package com.plugin.httprequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by LK on 2017/5/19.
 */

/**
 * Created by LK .
 * 注解扫描实现需要实现代理的接口，以此注解为查找依据
 * @author LK
 * @version 1.0
 * @data 2017-05-19 12:14
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component // 标明可被 Spring 扫描
public @interface HTTPRequest {

	/**
	 * 配置值
	 * @return
	 */
	Value value();

	/**
	 * 写入超时
	 * @return
	 */
	long writeTimeout() default 15;

	/**
	 * 读取超时
	 * @return
	 */
	long readTimeout() default 15;

	/**
	 * 链接超时
	 * @return
	 */
	long connectTimeout() default 15;


}
