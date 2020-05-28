package com.Spring.api;

/**
 * @author lk
 * @version 1.0
 * @date 2019/4/23 10:36
 */
public class ReqOutUser {
    Integer code;
    String msg;
    Object data;
    Boolean success;


    public Integer getCode() {
        return this.code;
    }

    public ReqOutUser setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public ReqOutUser setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public ReqOutUser setData(Object data) {
        this.data = data;
        return this;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public ReqOutUser setSuccess(Boolean success) {
        this.success = success;
        return this;
    }
}
