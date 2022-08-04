package com.wallet.walkthedog.dao;

import java.io.Serializable;

public class FeedDogFoodDao implements Serializable {
    private double addWeight;
    private double food;

    public double getAddWeight() {
        return addWeight;
    }

    public void setAddWeight(double addWeight) {
        this.addWeight = addWeight;
    }

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }
}