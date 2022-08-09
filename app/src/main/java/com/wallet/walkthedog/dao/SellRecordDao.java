package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class SellRecordDao implements Serializable {
    private int id;

    private int memberId;

    private String memberEmail;

    private String dogNumberChain;

    private int nftTypeCatagoryId;

    private double sum;

    private int status;

    private String free;

    private int type;

    private String createTime;

    private String token;

    private int toMemberId;

    private String toMemberEmail;

    private int nftBaseType;

    private String orderId;

    private String nftTypeName;

    private int sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getDogNumberChain() {
        return dogNumberChain;
    }

    public void setDogNumberChain(String dogNumberChain) {
        this.dogNumberChain = dogNumberChain;
    }

    public int getNftTypeCatagoryId() {
        return nftTypeCatagoryId;
    }

    public void setNftTypeCatagoryId(int nftTypeCatagoryId) {
        this.nftTypeCatagoryId = nftTypeCatagoryId;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getToMemberId() {
        return toMemberId;
    }

    public void setToMemberId(int toMemberId) {
        this.toMemberId = toMemberId;
    }

    public String getToMemberEmail() {
        return toMemberEmail;
    }

    public void setToMemberEmail(String toMemberEmail) {
        this.toMemberEmail = toMemberEmail;
    }

    public int getNftBaseType() {
        return nftBaseType;
    }

    public void setNftBaseType(int nftBaseType) {
        this.nftBaseType = nftBaseType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNftTypeName() {
        return nftTypeName;
    }

    public void setNftTypeName(String nftTypeName) {
        this.nftTypeName = nftTypeName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
