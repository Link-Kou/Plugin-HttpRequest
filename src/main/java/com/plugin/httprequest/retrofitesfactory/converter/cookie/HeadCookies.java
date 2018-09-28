package com.plugin.httprequest.retrofitesfactory.converter.cookie;

/**
 * 用于构建 Cookie
 * @author LK
 * @date 2018-04-02 21:15
 */
public class HeadCookies {

	private HeadCookie headCookie = new HeadCookie();

	public HeadCookies(InterfaceCookie c){
		c.get(headCookie);
	}

	public String get(){
		return headCookie.get();
	}


	public interface InterfaceCookie{
		void get(HeadCookie c);
	}

	public class HeadCookie{

		StringBuilder stringBuilder = new StringBuilder();

		public HeadCookie put(String key, String val){
				stringBuilder.append(key);
				stringBuilder.append("=");
				stringBuilder.append(val);
				stringBuilder.append(";");
			return this;
		}

		public String get(){
			String d = stringBuilder.toString();
			return d.substring(0,d.length()-1);
		}

	}
}