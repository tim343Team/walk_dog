package com.wallet.walkthedog.db.dao;

public class PropCache {
    private String uid;
    private String name;
    private String img;
    private String propNumberChain;
    private String dogId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getDogId() {
        return dogId;
    }

    public void setDogId(String dogId) {
        this.dogId = dogId;
    }
}
