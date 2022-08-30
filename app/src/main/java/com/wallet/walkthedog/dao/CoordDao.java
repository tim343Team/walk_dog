package com.wallet.walkthedog.dao;

public class CoordDao {
    private int logId;
    private long walkDogTime;
    private double walkDogKm;//单位米
    private int status;//0没有道具 1有新道具
    private int area;//1未知区域 2知名区域 3中间区域

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public long getWalkDogTime() {
        return walkDogTime;
    }

    public void setWalkDogTime(long walkDogTime) {
        this.walkDogTime = walkDogTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public double getWalkDogKm() {
        return walkDogKm;
    }

    public void setWalkDogKm(double walkDogKm) {
        this.walkDogKm = walkDogKm;
    }
}
