package com.wallet.walkthedog.dao.request;

public class OpreationPropRequest {
    private String propId;
    private String dogId;

    public OpreationPropRequest(String propId) {
        this.propId = propId;
    }

    public OpreationPropRequest(String propId, String dogId) {
        this.propId = propId;
        this.dogId = dogId;
    }

    public String getPropId() {
        return propId;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    public String getDogId() {
        return dogId;
    }

    public void setDogId(String dogId) {
        this.dogId = dogId;
    }
}
