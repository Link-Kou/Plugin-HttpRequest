package com.plugin.httprequest;

/**
 * 链式调用
 * @author LK
 * @date 2018-04-01 12:54
 */
public interface HttpResult<E,T> {

	E run(T t);

}
