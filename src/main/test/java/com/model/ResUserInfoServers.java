package com.model;

/**
 * @author LK
 * @date 2018/3/29
 * @description
 */
public class ResUserInfoServers {


	/**
	 * 状态
	 */
	private boolean success;

	/**
	 * 提示
	 */
	private String msg;

	/**
	 * 用户信息
	 */
	private userdata data;

	/**
	 * 状态
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 状态
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 提示
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 提示
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 用户信息
	 */
	public ResUserInfoServers.userdata.User getUser() {
		return this.data.getUser();
	}


	public class userdata{
		/**
		 * 用户信息
		 */
		private User user;

		/**
		 * 获取 用户信息
		 */
		public User getUser() {
			return this.user;
		}

		/**
		 * 设置 用户信息
		 */
		public userdata setUser(User user) {
			this.user = user;
			return this;
		}

		public class User {
			/**
			 * 用户id，可用与用户权限
			 */
			private int id;
			/**
			 * 用户邮箱
			 */
			private String uemail;
			/**
			 * 用户账号名称
			 */
			private String uname;
			/**
			 * 工号
			 */
			private String uids;
			/**
			 * 用户姓名
			 */
			private String username;
			/**
			 * 密码
			 */
			private String password;
			/**
			 * 平台名称
			 */
			private String appname;
			/**
			 * 邮箱
			 */
			private String email;
			/**
			 *部门
			 */
			private String department;
			/**
			 * 部门id
			 */
			private int departmentId;
			/**
			 * ip
			 */
			private String ip;
			/**
			 * 手机号码
			 */
			private String phone;
			/**
			 * 性别
			 */
			private int sex;


			/**
			 * 获取 用户id，可用与用户权限
			 */
			public int getId() {
				return this.id;
			}

			/**
			 * 设置 用户id，可用与用户权限
			 */
			public User setId(int id) {
				this.id = id;
				return this;
			}

			/**
			 * 获取 用户邮箱
			 */
			public String getUemail() {
				return this.uemail;
			}

			/**
			 * 设置 用户邮箱
			 */
			public User setUemail(String uemail) {
				this.uemail = uemail;
				return this;
			}

			/**
			 * 获取 用户账号名称
			 */
			public String getUname() {
				return this.uname;
			}

			/**
			 * 设置 用户账号名称
			 */
			public User setUname(String uname) {
				this.uname = uname;
				return this;
			}

			/**
			 * 获取 工号
			 */
			public String getUids() {
				return this.uids;
			}

			/**
			 * 设置 工号
			 */
			public User setUids(String uids) {
				this.uids = uids;
				return this;
			}

			/**
			 * 获取 用户姓名
			 */
			public String getUsername() {
				return this.username;
			}

			/**
			 * 设置 用户姓名
			 */
			public User setUsername(String username) {
				this.username = username;
				return this;
			}

			/**
			 * 获取 密码
			 */
			public String getPassword() {
				return this.password;
			}

			/**
			 * 设置 密码
			 */
			public User setPassword(String password) {
				this.password = password;
				return this;
			}

			/**
			 * 获取 平台名称
			 */
			public String getAppname() {
				return this.appname;
			}

			/**
			 * 设置 平台名称
			 */
			public User setAppname(String appname) {
				this.appname = appname;
				return this;
			}

			/**
			 * 获取 邮箱
			 */
			public String getEmail() {
				return this.email;
			}

			/**
			 * 设置 邮箱
			 */
			public User setEmail(String email) {
				this.email = email;
				return this;
			}

			/**
			 *部门
			 */
			public String getDepartment() {
				return this.department;
			}

			/**
			 *部门
			 */
			public User setDepartment(String department) {
				this.department = department;
				return this;
			}

			/**
			 * 获取 部门id
			 */
			public int getDepartmentId() {
				return this.departmentId;
			}

			/**
			 * 设置 部门id
			 */
			public User setDepartmentId(int departmentId) {
				this.departmentId = departmentId;
				return this;
			}

			/**
			 * 获取 ip
			 */
			public String getIp() {
				return this.ip;
			}

			/**
			 * 设置 ip
			 */
			public User setIp(String ip) {
				this.ip = ip;
				return this;
			}

			/**
			 * 获取 手机号码
			 */
			public String getPhone() {
				return this.phone;
			}

			/**
			 * 设置 手机号码
			 */
			public User setPhone(String phone) {
				this.phone = phone;
				return this;
			}

			/**
			 * 获取 性别
			 */
			public int getSex() {
				return this.sex;
			}

			/**
			 * 设置 性别
			 */
			public User setSex(int sex) {
				this.sex = sex;
				return this;
			}
		}
	}


}
