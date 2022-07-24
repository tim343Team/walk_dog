package com.wallet.walkthedog.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FriendInfoDao implements Serializable {
    private int id;

    private String nftName;

    private String name;

    private String img;

    private int sex;

    private int level;

    private int walkTheDogKm;

    private int walkTheDogCount;

    private long walkTheDogTime;

    private int status;

    private List<PropDao> propList=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNftName() {
        return nftName;
    }

    public void setNftName(String nftName) {
        this.nftName = nftName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWalkTheDogKm() {
        return walkTheDogKm;
    }

    public void setWalkTheDogKm(int walkTheDogKm) {
        this.walkTheDogKm = walkTheDogKm;
    }

    public int getWalkTheDogCount() {
        return walkTheDogCount;
    }

    public void setWalkTheDogCount(int walkTheDogCount) {
        this.walkTheDogCount = walkTheDogCount;
    }

    public long getWalkTheDogTime() {
        return walkTheDogTime;
    }

    public void setWalkTheDogTime(long walkTheDogTime) {
        this.walkTheDogTime = walkTheDogTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<PropDao> getPropList() {
        return propList;
    }

    public void setPropList(List<PropDao> propList) {
        this.propList = propList;
    }


}