package com.wallet.walkthedog.dao.request;

public class TrainRequest {
    private String dogId;
    private String trainId;

    public TrainRequest(String dogId, String trainId) {
        this.dogId = dogId;
        this.trainId = trainId;
    }

    public String getDogId() {
        return dogId;
    }

    public void setDogId(String dogId) {
        this.dogId = dogId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }
}
