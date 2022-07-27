package com.wallet.walkthedog.dao;

import java.io.Serializable;
import java.util.List;

public class FriendInfoListDao implements Serializable {
    private List<FriendInfoDao> records;

    public List<FriendInfoDao> getRecords() {
        return records;
    }

    public void setRecords(List<FriendInfoDao> records) {
        this.records = records;
    }
}