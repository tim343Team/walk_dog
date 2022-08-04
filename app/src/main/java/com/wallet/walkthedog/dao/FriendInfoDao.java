package com.wallet.walkthedog.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FriendInfoDao implements Serializable {
    private String id;

    private String name;

    private String getDogNumberChain;

    private int nftTypeCatagoryId;

    private String img;

    private String dogNumberChain;

    private int sex;

    private double level;

    private int type;//1背包 2出售 3使用中

    private int status;

    private double walkTheDogKm;

    private int walkTheDogCount;//总次数

    private int dayLimit;//今日次数

    private long walkTheDogTime;//总时长（秒）

    private double weight;

    private double decimalDog;

    private String props;

    private String feedTime;

    private String memberId;

    private String createTime;

    private int sysDelete;

    private double price;

    private String token;

    private int trainingCount;

    private String nickName;

    private String email;

    private int rateOfProgress;

    private int starvation;//1饥饿 2正常

    private List<PropDao> propList=new ArrayList<>();

    //一起遛狗信息
    private String nftName;

    private String friendMemberId;

    private String friendListId;

    private String friendName;

    private String friendNickName;

    private String friendWalkTheDogKm;

    private String friendWalkTheDogCount;

    private String friendWalkTheDogTime;

    private String dogName;


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

    public String getGetDogNumberChain() {
        return getDogNumberChain;
    }

    public void setGetDogNumberChain(String getDogNumberChain) {
        this.getDogNumberChain = getDogNumberChain;
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

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
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

    public int getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(int dayLimit) {
        this.dayLimit = dayLimit;
    }

    public long getWalkTheDogTime() {
        return walkTheDogTime;
    }

    public void setWalkTheDogTime(long walkTheDogTime) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public int getRateOfProgress() {
        return rateOfProgress;
    }

    public void setRateOfProgress(int rateOfProgress) {
        this.rateOfProgress = rateOfProgress;
    }

    public int getStarvation() {
        return starvation;
    }

    public void setStarvation(int starvation) {
        this.starvation = starvation;
    }

    public List<PropDao> getPropList() {
        return propList;
    }

    public void setPropList(List<PropDao> propList) {
        this.propList = propList;
    }

    public String getNftName() {
        return nftName;
    }

    public void setNftName(String nftName) {
        this.nftName = nftName;
    }

    public String getFriendMemberId() {
        return friendMemberId;
    }

    public void setFriendMemberId(String friendMemberId) {
        this.friendMemberId = friendMemberId;
    }

    public String getFriendListId() {
        return friendListId;
    }

    public void setFriendListId(String friendListId) {
        this.friendListId = friendListId;
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

    public String getFriendWalkTheDogKm() {
        return friendWalkTheDogKm;
    }

    public void setFriendWalkTheDogKm(String friendWalkTheDogKm) {
        this.friendWalkTheDogKm = friendWalkTheDogKm;
    }

    public String getFriendWalkTheDogCount() {
        return friendWalkTheDogCount;
    }

    public void setFriendWalkTheDogCount(String friendWalkTheDogCount) {
        this.friendWalkTheDogCount = friendWalkTheDogCount;
    }

    public String getFriendWalkTheDogTime() {
        return friendWalkTheDogTime;
    }

    public void setFriendWalkTheDogTime(String friendWalkTheDogTime) {
        this.friendWalkTheDogTime = friendWalkTheDogTime;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }
}