package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class BoxDao implements Serializable {
    private int catId;
    private int number;
    private int propId;
    private String propImg;
    private String propName;

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public String getPropImg() {
        return propImg;
    }

    public void setPropImg(String propImg) {
        this.propImg = propImg;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }
}
