package com.model;

/**
 *
 * @author: LK
 * @date: 2018/3/21
 * @description:
 */
public class ResFixedTokenServes {

	/**
	 * 数据
	 */
	private DataToken data;

	/**
	 * 状态
	 */
	private boolean success;

	/**
	 * 提示
	 */
	private String msg;

	/**
	 * 获取 Token数据
	 */
	public String getToken() {
		if(this.data != null){
			return this.data.getToken();
		}
		return null;
	}


	/**
	 * 获取 状态
	 */
	public boolean isSuccess() {
		return this.success;
	}

	/**
	 * 提示
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 类型
	 */
	private class DataToken {
		/**
		 * token
		 */
		private String token;


		/**
		 * 获取 token
		 */
		public String getToken() {
			return this.token;
		}

		/**
		 * 设置 token
		 */
		public DataToken setToken(String token) {
			this.token = token;
			return this;
		}
	}
}
