package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class DogInfoDao implements Serializable {

    private String id;

    private String name;

    private int nftTypeCatagoryId;

    private String img;

    private String dogNumberChain;

    private int sex;

    private int level;

    private int type;

    private int status;

    private long walkTheDogKm;

    private int walkTheDogCount;//总次数

    private int dayLimit;//今日次数

    private String walkTheDogTime;//总时长（秒）

    private double weight;

    private double decimalDog;

    private String props;

    private String feedTime;

    private String memberId;

    private String createTime;

    private int sysDelete;

    private int price;

    private String token;

    private int trainingCount;

    private String nickName;

    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNftTypeCatagoryId() {
        return nftTypeCatagoryId;
    }

    public void setNftTypeCatagoryId(int nftTypeCatagoryId) {
        this.nftTypeCatagoryId = nftTypeCatagoryId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDogNumberChain() {
        return dogNumberChain;
    }

    public void setDogNumberChain(String dogNumberChain) {
        this.dogNumberChain = dogNumberChain;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getWalkTheDogKm() {
        return walkTheDogKm;
    }

    public void setWalkTheDogKm(long walkTheDogKm) {
        this.walkTheDogKm = walkTheDogKm;
    }

    public int getWalkTheDogCount() {
        return walkTheDogCount;
    }

    public void setWalkTheDogCount(int walkTheDogCount) {
        this.walkTheDogCount = walkTheDogCount;
    }

    public String getWalkTheDogTime() {
        return walkTheDogTime;
    }

    public void setWalkTheDogTime(String walkTheDogTime) {
        this.walkTheDogTime = walkTheDogTime;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDecimalDog() {
        return decimalDog;
    }

    public void setDecimalDog(double decimalDog) {
        this.decimalDog = decimalDog;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public String getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(String feedTime) {
        this.feedTime = feedTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getSysDelete() {
        return sysDelete;
    }

    public void setSysDelete(int sysDelete) {
        this.sysDelete = sysDelete;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTrainingCount() {
        return trainingCount;
    }

    public void setTrainingCount(int trainingCount) {
        this.trainingCount = trainingCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(int dayLimit) {
        this.dayLimit = dayLimit;
    }
}
