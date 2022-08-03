package com.wallet.walkthedog.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FriendInfoDao implements Serializable {
    private int id;

    private String getDogNumberChain;

    private int friendMemberId;

    private int friendListId;

    private String nftName;

    private String dogName;

    private String friendName;

    private String friendNickName;

    private String name;

    private int sex;

    private double level;

    private double walkTheDogKm;

    private int walkTheDogCount;

    private long walkTheDogTime;

    private String propList;

    private String img;

    private int starvation;

    private double rateOfProgress;

    private String friendWalkTheDogKm;

    private int friendWalkTheDogCount;

    private int friendWalkTheDogTime;

    private int dayLimit;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGetDogNumberChain() {
        return getDogNumberChain;
    }

    public void setGetDogNumberChain(String getDogNumberChain) {
        this.getDogNumberChain = getDogNumberChain;
    }

    public void setDayLimit(int dayLimit) {
        this.dayLimit = dayLimit;
    }

    public int getFriendMemberId() {
        return friendMemberId;
    }

    public void setFriendMemberId(int friendMemberId) {
        this.friendMemberId = friendMemberId;
    }

    public int getFriendListId() {
        return friendListId;
    }

    public void setFriendListId(int friendListId) {
        this.friendListId = friendListId;
    }

    public String getNftName() {
        return nftName;
    }

    public void setNftName(String nftName) {
        this.nftName = nftName;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendNickName() {
        return friendNickName;
    }

    public void setFriendNickName(String friendNickName) {
        this.friendNickName = friendNickName;
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

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public double getWalkTheDogKm() {
        return walkTheDogKm;
    }

    public void setWalkTheDogKm(double walkTheDogKm) {
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

    public String getPropList() {
        return propList;
    }

    public void setPropList(String propList) {
        this.propList = propList;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStarvation() {
        return starvation;
    }

    public void setStarvation(int starvation) {
        this.starvation = starvation;
    }

    public double getRateOfProgress() {
        return rateOfProgress;
    }

    public void setRateOfProgress(double rateOfProgress) {
        this.rateOfProgress = rateOfProgress;
    }

    public String getFriendWalkTheDogKm() {
        return friendWalkTheDogKm;
    }

    public void setFriendWalkTheDogKm(String friendWalkTheDogKm) {
        this.friendWalkTheDogKm = friendWalkTheDogKm;
    }

    public int getFriendWalkTheDogCount() {
        return friendWalkTheDogCount;
    }

    public void setFriendWalkTheDogCount(int friendWalkTheDogCount) {
        this.friendWalkTheDogCount = friendWalkTheDogCount;
    }

    public int getFriendWalkTheDogTime() {
        return friendWalkTheDogTime;
    }

    public void setFriendWalkTheDogTime(int friendWalkTheDogTime) {
        this.friendWalkTheDogTime = friendWalkTheDogTime;
    }


    public int getDayLimit() {
        return dayLimit;
    }
}