package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class PropMailDao implements Serializable {
    private int id;

    private int nftTypeCatagoryId;

    private String name;

    private String img;

    private String propNumberChain;

    private String level;

    private int dogId;

    private int type;

    private String describeData;

    private String createTime;

    private String memberId;

    private int decorateDogId;

    private double price;

    private String token;

    private String rarity;

    private String email;

    private String usercp;

    private String catNmae;

    private String dogNumberChain;

    private String dogName;

    private int dogUserId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNftTypeCatagoryId() {
        return nftTypeCatagoryId;
    }

    public void setNftTypeCatagoryId(int nftTypeCatagoryId) {
        this.nftTypeCatagoryId = nftTypeCatagoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPropNumberChain() {
        return propNumberChain;
    }

    public void setPropNumberChain(String propNumberChain) {
        this.propNumberChain = propNumberChain;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescribeData() {
        return describeData;
    }

    public void setDescribeData(String describeData) {
        this.describeData = describeData;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getDecorateDogId() {
        return decorateDogId;
    }

    public void setDecorateDogId(int decorateDogId) {
        this.decorateDogId = decorateDogId;
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

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsercp() {
        return usercp;
    }

    public void setUsercp(String usercp) {
        this.usercp = usercp;
    }

    public String getCatNmae() {
        return catNmae;
    }

    public void setCatNmae(String catNmae) {
        this.catNmae = catNmae;
    }

    public String getDogNumberChain() {
        return dogNumberChain;
    }

    public void setDogNumberChain(String dogNumberChain) {
        this.dogNumberChain = dogNumberChain;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public int getDogUserId() {
        return dogUserId;
    }

    public void setDogUserId(int dogUserId) {
        this.dogUserId = dogUserId;
    }
}
