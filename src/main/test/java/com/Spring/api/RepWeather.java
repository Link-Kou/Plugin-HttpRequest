package com.Spring.api;

import java.util.List;

/**
 * 部分参数-完整版查看 @see {https://lbs.amap.com/api/webservice/guide/api/weatherinfo}
 *
 * @author lk
 * @version 1.0
 * @date 2019/4/17 18:45
 */
public class RepWeather {
    /**
     * 返回状态
     * 值为0或1 1：成功；0：失败
     */
    private Integer status;
    /**
     * 返回结果总数目
     */
    private String count;
    /**
     * 返回的状态信息
     */
    private String info;
    /**
     * 返回状态说明,10000代表正确
     */
    private String infocode;


    /**
     * 返回状态
     * 值为0或1 1：成功；0：失败
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 返回状态
     * 值为0或1 1：成功；0：失败
     */
    public RepWeather setStatus(Integer status) {
        this.status = status;
        return this;
    }

    /**
     * 获取 返回结果总数目
     */
    public String getCount() {
        return this.count;
    }

    /**
     * 设置 返回结果总数目
     */
    public RepWeather setCount(String count) {
        this.count = count;
        return this;
    }

    /**
     * 获取 返回的状态信息
     */
    public String getInfo() {
        return this.info;
    }

    /**
     * 设置 返回的状态信息
     */
    public RepWeather setInfo(String info) {
        this.info = info;
        return this;
    }

    /**
     * 获取 返回状态说明,10000代表正确
     */
    public String getInfocode() {
        return this.infocode;
    }

    /**
     * 设置 返回状态说明,10000代表正确
     */
    public RepWeather setInfocode(String infocode) {
        this.infocode = infocode;
        return this;
    }


}
