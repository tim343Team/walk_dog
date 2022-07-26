package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class TrainDao implements Serializable {
    private int id;

    private String name;

    private double consume;

    private String describe;

    private int consumeType;

    private double reap;

    private int reapType;

    private String createTime;

    private String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getConsume() {
        return consume;
    }

    public void setConsume(double consume) {
        this.consume = consume;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(int consumeType) {
        this.consumeType = consumeType;
    }

    public double getReap() {
        return reap;
    }

    public void setReap(double reap) {
        this.reap = reap;
    }

    public int getReapType() {
        return reapType;
    }

    public void setReapType(int reapType) {
        this.reapType = reapType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
