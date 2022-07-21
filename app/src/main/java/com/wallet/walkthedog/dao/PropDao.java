package com.wallet.walkthedog.dao;

public class PropDao {
    private String id;

    private int nftTypeCatagoryId;

    private String name;

    private String img;

    private String propNumberChain;

    private String level;

    private int dogId;

    private int type;//1使用中 2背包 3出售 ,

    private String describeData;

    private String createTime;

    private int memberId;

    private double price;

    private String token;

    private String rarity;

    private String email;

    private String usercp;

    private String catNmae;

    private String dogNumberChain;

    private int dogUserId;

//    public boolean isSelect() {
//        return isSelect;
//    }
//
//    public void setSelect(boolean select) {
//        isSelect = select;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return type;////1使用中 2背包 3出售 ,
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

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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

    public int getDogUserId() {
        return dogUserId;
    }

    public void setDogUserId(int dogUserId) {
        this.dogUserId = dogUserId;
    }
}
