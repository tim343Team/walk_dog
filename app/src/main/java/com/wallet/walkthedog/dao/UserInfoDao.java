package com.wallet.walkthedog.dao;

import java.util.ArrayList;
import java.util.List;

public class UserInfoDao {
    private Object walletId;
    private int propCount;
    private String walkTheDogCount;
    private int friendCount;
    private String dogCount;
    private String walkTheDogTime;
    private List<WalletsItem> wallets;
    private String token;
    private String welcomeCode;
    private String password;
    private Object createTime;
    private String name;
    private int id;
    private String email;
    private String walkTheDogKm;
    private String invitationCode;
    private String chatHead;

    public Object getWalletId() {
        return walletId;
    }

    public int getPropCount() {
        return propCount;
    }

    public String getWalkTheDogCount() {
        return walkTheDogCount;
    }

    public int getFriendCount() {
        return friendCount;
    }

    public String getDogCount() {
        return dogCount;
    }

    public String getWalkTheDogTime() {
        return walkTheDogTime;
    }

    public List<WalletsItem> getWallets() {
        if (wallets == null) {
            wallets = new ArrayList<>();
        }
        return wallets;
    }

    public String getToken() {
        return token;
    }

    public String getWelcomeCode() {
        return welcomeCode;
    }

    public String getPassword() {
        return password;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getWalkTheDogKm() {
        return walkTheDogKm;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public String getChatHead() {
        return chatHead;
    }

}