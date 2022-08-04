package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class InvitedFriendDao implements Serializable {
    private UserInfoDao user;
    private FriendInfoDao dog;
    private int status;//-1是dog为空 1是好友在遛狗 2等待遛狗 3是训练中

    public UserInfoDao getUser() {
        return user;
    }

    public void setUser(UserInfoDao user) {
        this.user = user;
    }

    public FriendInfoDao getDog() {
        return dog;
    }

    public void setDog(FriendInfoDao dog) {
        this.dog = dog;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
