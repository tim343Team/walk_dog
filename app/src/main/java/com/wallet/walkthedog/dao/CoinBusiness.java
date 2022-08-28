package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class CoinBusiness implements Serializable {
    private String name;
    private String coldWalletAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColdWalletAddress() {
        return coldWalletAddress;
    }

    public void setColdWalletAddress(String coldWalletAddress) {
        this.coldWalletAddress = coldWalletAddress;
    }
}
