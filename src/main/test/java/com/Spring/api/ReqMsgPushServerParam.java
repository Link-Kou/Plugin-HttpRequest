package com.Spring.api;

/**
 * 统一消息服务调用返回结构
 * @author LK
 * @version 1.0
 * @date 2017-12-22 21:38
 */
public class ReqMsgPushServerParam {

	/**
	 * 成功与否
	 */
	private boolean success;


	/**
	 * 获取 成功与否
	 */
	public boolean isSuccess() {
		return this.success;
	}

	/**
	 * 设置 成功与否
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
}