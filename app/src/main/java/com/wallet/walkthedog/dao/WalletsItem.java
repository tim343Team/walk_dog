package com.wallet.walkthedog.dao;

public class WalletsItem {
    private String address;
    private Object freezeDogFood;
    private String createTime;
    private int id;
    private double dogFood;
    private int type;
    private int memberId;

    public String getAddress() {
        return address;
    }

    public Object getFreezeDogFood() {
        return freezeDogFood;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getId() {
        return id;
    }

    public double getDogFood() {
        return dogFood;
    }

    public int getType() {
        return type;
    }

    public int getMemberId() {
        return memberId;
    }
}
