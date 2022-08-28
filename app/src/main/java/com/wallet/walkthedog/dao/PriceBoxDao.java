package com.wallet.walkthedog.dao;

public class PriceBoxDao {
    private String name;
    private String direction;//传asc 升序  desc降序

    public PriceBoxDao(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
