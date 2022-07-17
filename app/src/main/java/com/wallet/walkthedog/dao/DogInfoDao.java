package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class DogInfoDao implements Serializable {
    private int sex;

    private int level;

    private int type;

    private int status;

    private long walkTheDogKm;

    private int walkTheDogCount;

    private String walkTheDogTime;

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
}
