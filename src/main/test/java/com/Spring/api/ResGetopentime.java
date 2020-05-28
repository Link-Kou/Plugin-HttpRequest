package com.Spring.api;

/**
 * @author lk
 * @version 1.0
 * @date 2019/4/23 10:36
 */
public class ResGetopentime {
    /**
     * 版本
     */
    private String version;

    private String devTokenId;

    private String business;

    private String platform;

    private String system;


    /**
     * 获取 版本
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * 设置 版本
     */
    public ResGetopentime setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getDevTokenId() {
        return this.devTokenId;
    }

    public ResGetopentime setDevTokenId(String devTokenId) {
        this.devTokenId = devTokenId;
        return this;
    }

    public String getBusiness() {
        return this.business;
    }

    public ResGetopentime setBusiness(String business) {
        this.business = business;
        return this;
    }

    public String getPlatform() {
        return this.platform;
    }

    public ResGetopentime setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public String getSystem() {
        return this.system;
    }

    public ResGetopentime setSystem(String system) {
        this.system = system;
        return this;
    }
}
