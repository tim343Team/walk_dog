package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class VersionInfoDao implements Serializable {
    private int id;
    private String downloadUrl;
    private String updateTime;
    private String remark;
    private String version;
    private int platform;
    private String wxUser;
    private String telegramUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getWxUser() {
        return wxUser;
    }

    public void setWxUser(String wxUser) {
        this.wxUser = wxUser;
    }

    public String getTelegramUser() {
        return telegramUser;
    }

    public void setTelegramUser(String telegramUser) {
        this.telegramUser = telegramUser;
    }
}
