package com.plugin.httprequest.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 获取到配置信息
 * @author LK
 * @version 1.0
 * @data 2017-12-12 17:31
 */
/*@Component*/
public class HttpProperties {

	private static Map<String,String> ctxPropMap = new HashMap<String, String>(16);

	public HttpProperties(){}

	public HttpProperties(Properties props){
		for (String key : props.stringPropertyNames()) {
			ctxPropMap.put(key,props.getProperty(key));
		}
	}

	/**
	 * 获取注解
	 * @param name
	 * @return
	 */
	public static String getCtxProp(String name) {
		return ctxPropMap.get(name);
	}

	/**
	 * 获取HashMap键值对
	 * @return
	 */
	public static Map<String, String> getCtxPropMap() {
		return ctxPropMap;
	}

}