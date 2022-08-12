package com.wallet.walkthedog.dao.request;

public class CardEditRequset {
    //bank
    private String realName;
    private String bank;
    private String cardNo;
    private String jyPassword;
    //swift
    private String swiftAddress;
    private String swiftRealName;
    private String password;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getJyPassword() {
        return jyPassword;
    }

    public void setJyPassword(String jyPassword) {
        this.jyPassword = jyPassword;
    }

    public String getSwiftAddress() {
        return swiftAddress;
    }

    public void setSwiftAddress(String swiftAddress) {
        this.swiftAddress = swiftAddress;
    }

    public String getSwiftRealName() {
        return swiftRealName;
    }

    public void setSwiftRealName(String swiftRealName) {
        this.swiftRealName = swiftRealName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
