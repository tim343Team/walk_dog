package com.wallet.walkthedog.dao;

public class AwardDao {
    private String catImg;
    private String catName;
    private String createTime;
    private int id;
    private int status;//1待领取 2过期 3已领取

    public String getCatImg() {
        return catImg;
    }

    public void setCatImg(String catImg) {
        this.catImg = catImg;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;//1待领取 2过期 3已领取
    }

    public void setStatus(int status) {
        this.status = status;
    }
}